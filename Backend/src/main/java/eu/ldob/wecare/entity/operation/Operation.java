package eu.ldob.wecare.entity.operation;

import eu.ldob.wecare.entity.user.Dispatcher;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Operation {

    private int internalId;
    private String id;
    private Incident incident;
    private EStatus status;
    private Dispatcher dispatcher;
    private List<FirstResponder> responderAlarmed;
    private List<FirstResponder> responderInAction;

    private Map<EStatus,Date> timestamps = new HashMap<>();

    private GPSCoordinates currentCoordinates;

    public Operation() { }

    public Operation(int internalId, String id, Dispatcher dispatcher, Incident incident) {

        this.internalId = internalId;
        this.id = id;
        this.dispatcher = dispatcher;
        this.incident = incident;

        this.setStatus(EStatus.RECEIVED);

        // TODO get gps coordinates
        this.currentCoordinates = new GPSCoordinates(0, 0);
    }

    public enum EStatus {
        RECEIVED("Empfangen", "--"),
        ALARMED("Alarmiert", "Neuer Einsatz"),
        ACCEPTED("Angenommen", "Anfahrt"),
        ARRIVED("Eingetroffen", "Am Einsatzort"),
        FINISHED("Beendet", "Bericht erstellen"),
        DOCUMENTED("Dokumentiert", "Zusammenfassung"),
        CANCELED("Abgebrochen", "Nicht teilgenommen");

        private String name;
        private String title;

        EStatus(String name, String title) {
            this.name = name;
            this.title = title;
        }

        public String getName() {
            return name;
        }

        public String getTitle() {
            return title;
        }
    }

    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void setStatus(EStatus status) {
        this.setStatus(status, new Date());
    }
    public void setStatus(EStatus status, Date date) {
        this.status = status;
        timestamps.put(this.status, date);
    }

    public int getInternalId() {
        return internalId;
    }

    public String getId() {
        return id;
    }

    public EStatus getStatus() {
        if(status == null) {
            return EStatus.RECEIVED;
        }

        return status;
    }

    public Dispatcher getDispatcher() { return dispatcher; }

    public Map<EStatus,Date> getTimestamps() {
        return timestamps;
    }

    public Incident getIncident() {
        return incident;
    }
}
