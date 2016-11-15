package eu.ldob.wecare.service.rest;

import eu.ldob.wecare.entity.rest.AuthenticationData;
import eu.ldob.wecare.entity.rest.AuthenticationToken;
import eu.ldob.wecare.entity.user.AUser;

public interface ILoginService {

    void postLogin(RestListener<AuthenticationToken> listener, AuthenticationData data);

    void postLogout(RestListener<Void> listener, AuthenticationToken token);

    void postFirebaseToken(RestListener<Boolean> listener, String firebaseToken, AuthenticationToken token);

    void getUser(RestListener<? extends AUser> listener, AuthenticationToken token);
}