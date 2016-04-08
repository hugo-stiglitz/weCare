package eu.ldob.wecare.entity.operation;

public class Location {

    private GPSCoordinates gps;
    private Address address;

    public GPSCoordinates getGps() { return gps; }

    public void distanceTo(Location location) {
        this.gps.distanceTo(location.getGps());
    }

    private class GPSCoordinates {

        private float lat;
        private float lng;

        public float distanceTo(GPSCoordinates gps) {
            float distance = -1;

            //TODO implement

            return distance;
        }
    }

    private class Address {

        private String name;
        private String city;
        private String zipCode;
    }
}
