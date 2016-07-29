package eu.ldob.wecare.entity.operation;

public class Location {

    private GPSCoordinates gps;
    private Address address;

    public Location(Address address, GPSCoordinates gps) {

        this.address = address;
        this.gps = gps;
    }

    public Address getAddress() {
        return address;
    }

    public GPSCoordinates getCoordinates() {
        return gps;
    }
}
