package eu.ldob.wecare.entity.operation;

import eu.ldob.wecare.app.util.FormatUtil;
import eu.ldob.wecare.entity.user.Dispatcher;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Operation {

    private long internalId;
    private String id;
    private Incident incident;
    private EStatus status;
    private Dispatcher dispatcher;
    private List<FirstResponder> responderAlarmed;
    private List<FirstResponder> responderInAction;

    private Map<EStatus,Date> timestamps = new HashMap<>();

    private GPSCoordinates currentCoordinates;

    public Operation(long internalId, String id, Dispatcher dispatcher, Incident incident) {

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
        DOCUMENTED("Empfangen", "Zusammenfassung"),
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

        public EStatus[] getAllStatus() {
            return EStatus.values();
        }
    }

    public void setStatus(EStatus status) {
        this.status = status;
        timestamps.put(this.status, new Date());
    }

    public String getId() {
        return id;
    }

    public EStatus getStatus() {
        return status;
    }

    public Map<EStatus,Date> getTimestamps() {
        return timestamps;
    }

    public String getMessage() {
        return incident.getMessage();
    }

    public Patient getPatient() {
        return incident.getPatient();
    }

    public Location getLocation() {
        return incident.getLocation();
    }

    public String getMyDistance() {
        return FormatUtil.distanceToString(currentCoordinates.distanceTo(incident.getLocation().getCoordinates()));
    }

    public String getAmbulanceDistance() {
        return FormatUtil.distanceToString(4398.329953f);
    }
}
