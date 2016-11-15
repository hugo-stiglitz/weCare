package eu.ldob.wecare.entity.operation;

public class Incident {

    private String message;
    private Patient patient;
    private Location location;

    public Incident() { }

    public Incident(String message, Patient patient, Location location) {

        this.message = message;
        this.patient = patient;
        this.location = location;
    }

    public String getMessage() {
        return message;
    }

    public Patient getPatient() {
        return patient;
    }

    public Location getLocation() {
        return location;
    }
}
