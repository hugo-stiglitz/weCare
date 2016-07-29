package eu.ldob.wecare.entity.operation;

public class GPSCoordinates {

    private float lat;
    private float lng;

    public GPSCoordinates(float lat, float lng) {

        this.lat = lat;
        this.lng = lng;
    }

    public float distanceTo(GPSCoordinates gps) {
        float distance = 1853.3546f;

        //TODO implement

        return distance;
    }
}
