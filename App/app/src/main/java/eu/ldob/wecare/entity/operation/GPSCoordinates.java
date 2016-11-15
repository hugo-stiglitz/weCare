package eu.ldob.wecare.entity.operation;

public class GPSCoordinates {

    private double lat;
    private double lng;

    public GPSCoordinates(double lat, double lng) {

        this.lat = lat;
        this.lng = lng;
    }

    public double getLatitude() {
        return lat;
    }

    public double getLongitude() {
        return lng;
    }

    public double distanceTo(GPSCoordinates gps) {
        return calculateDistance(this.lat, gps.lat, this.lng, gps.lng);
    }

    /*
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point
     * lat2, lon2 End point
     * @returns Distance in Meters
     */
    private double calculateDistance(double lat1, double lat2, double lon1, double lon2) {

        final int R = 6371; // Radius of the earth

        Double latDistance = Math.toRadians(lat2 - lat1);
        Double lonDistance = Math.toRadians(lon2 - lon1);

        Double a =
                Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c * 1000;                    // convert to meters
    }
}
