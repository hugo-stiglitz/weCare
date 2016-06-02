package eu.ldob.wecare.entity.operation;

import eu.ldob.wecare.entity.user.Dispatcher;

import java.util.List;

public class Operation {

    private long internalId;
    private String id;
    private Incident incident;
    private EStatus status;
    private Dispatcher dispatcher;
    private List<FirstResponder> responderAlarmed;
    private List<FirstResponder> responderInAction;

    public Operation(long internalId, String id) {
        this.internalId = internalId;
        this.id = id;
    }

    private enum EStatus {
        RECEIVED, ALARMED, ACCEPTED, FINISHED, DOCUMENTED
    }

    public String getId() {
        return id;
    }

    public String getInfo() {
        return "Test-Info";
    }
}
