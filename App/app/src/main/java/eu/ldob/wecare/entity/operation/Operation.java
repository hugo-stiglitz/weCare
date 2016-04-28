package eu.ldob.wecare.entity.operation;

import eu.ldob.wecare.entity.user.Dispatcher;

import java.util.List;

public class Operation {

    private Incident incident;
    private EStatus status;
    private Dispatcher dispatcher;
    private List<FirstResponder> responderAlarmed;
    private List<FirstResponder> responderInAction;

    private enum EStatus {
        RECEIVED, ALARMED, ACCEPTED, FINISHED
    }
}
