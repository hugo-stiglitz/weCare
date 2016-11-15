package eu.ldob.wecare.service.rest;

import eu.ldob.wecare.entity.rest.AuthenticationData;
import eu.ldob.wecare.entity.rest.AuthenticationToken;
import eu.ldob.wecare.entity.user.AUser;

public class LoginService implements ILoginService {

    private static final String URL_ = "http://localhost:8080/";
    private static final String LOGIN = "login";
    private static final String LOGOUT = "logout";
    private static final String FIREBASETOKEN = "firebasetoken";
    private static final String USER = "user";

    @Override
    public void postLogin(RestListener<AuthenticationToken> listener, AuthenticationData data) {
        new Thread(new PostLogin(listener, data)).start();
    }

    @Override
    public void postLogout(RestListener<Void> listener, AuthenticationToken token) {
        //TODO
    }

    @Override
    public void postFirebaseToken(RestListener<Boolean> listener, String firebaseToken, AuthenticationToken token) {
        new Thread(new PostFirebaseToken(listener, token, firebaseToken)).start();
    }

    @Override
    public void getUser(RestListener<? extends AUser> listener, AuthenticationToken token) {
        new Thread(new GetUser(token, (RestListener<AUser>) listener)).start();
    }



    /*
     * RUNNABLES
     */

    private class PostLogin implements Runnable {

        private RestListener<AuthenticationToken> listener;
        private AuthenticationData authenticationData;
        public PostLogin(RestListener<AuthenticationToken> listener, AuthenticationData authenticationData) {
            this.listener = listener;
            this.authenticationData = authenticationData;
        }

        @Override
        public void run() {
            PostRequest<AuthenticationData, AuthenticationToken> rest = new PostRequest<>(null, AuthenticationToken.class);
            rest.post(URL_ + LOGIN, listener, authenticationData);
        }
    }

    private class PostFirebaseToken implements Runnable {

        private RestListener<Boolean> listener;
        private AuthenticationToken token;
        private String firebaseToken;
        public PostFirebaseToken(RestListener<Boolean> listener, AuthenticationToken token, String firebaseToken) {
            this.listener = listener;
            this.token = token;
            this.firebaseToken = firebaseToken;
        }

        @Override
        public void run() {
            PostRequest<String, Boolean> rest = new PostRequest<>(token, Boolean.class);
            rest.post(URL_ + FIREBASETOKEN, listener, firebaseToken);
        }
    }

    private class GetUser implements Runnable {

        private AuthenticationToken token;
        private RestListener<AUser> listener;
        public GetUser(AuthenticationToken token, RestListener<AUser> listener) {
            this.token = token;
            this.listener = listener;
        }

        @Override
        public void run() {
            GetRequest<AUser> rest = new GetRequest<>(token, AUser.class);
            rest.get(URL_ + USER, listener);
        }
    }
}
