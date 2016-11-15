package eu.ldob.wecare.entity.operation;

public class Location {

    private GPSCoordinates coordinates;
    private Address address;

    public Location() { }

    public Location(Address address, GPSCoordinates coordinates) {

        this.address = address;
        this.coordinates = coordinates;
    }

    public Address getAddress() {
        return address;
    }

    public GPSCoordinates getCoordinates() {
        return coordinates;
    }
}
