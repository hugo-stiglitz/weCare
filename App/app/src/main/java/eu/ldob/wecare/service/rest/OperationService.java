package eu.ldob.wecare.service.rest;

import java.util.List;

import eu.ldob.wecare.entity.operation.Operation;
import eu.ldob.wecare.entity.rest.AuthenticationData;
import eu.ldob.wecare.entity.rest.AuthenticationToken;
import eu.ldob.wecare.entity.rest.OperationList;
import eu.ldob.wecare.entity.user.AUser;

public class OperationService /*implements IOperationService*/ {

    private static final String URL_ = "http://localhost:8080/";
    private static final String OPERATIONS = "operations";
    private static final String OPERATION_ = "operation/";

    private static final String _STATUS = "/status";

    private AuthenticationToken token;

    public OperationService(AuthenticationToken token) {
        this.token = token;
    }

    public void getAllOperations(RestListener<OperationList> listener) {
        new Thread(new GetAllOperations(listener)).start();
    }

    public void postStatus(RestListener<Boolean> listener, long id, Operation.EStatus status) {
        new Thread(new PostStatus(listener, id, status)).start();
    }



    /*
     * RUNNABLES
     */


    private class GetAllOperations implements Runnable {

        private RestListener<OperationList> listener;
        public GetAllOperations(RestListener<OperationList> listener) {
            this.listener = listener;
        }

        @Override
        public void run() {
            GetRequest<OperationList> rest = new GetRequest<>(token, OperationList.class);
            rest.get(URL_ + OPERATIONS, listener);
        }
    }

    private class PostStatus implements Runnable {

        private RestListener<Boolean> listener;
        private long id;
        private Operation.EStatus status;
        public PostStatus(RestListener<Boolean> listener, long id, Operation.EStatus status) {
            this.listener = listener;
            this.id = id;
            this.status = status;
        }

        @Override
        public void run() {
            PostRequest<Operation.EStatus, Boolean> rest = new PostRequest<>(token, Boolean.class);
            rest.post(URL_ + OPERATION_ + id + _STATUS, listener, status);
        }
    }
}
