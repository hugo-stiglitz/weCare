package eu.ldob.wecare.app.util;

public class FormatUtil {

    private FormatUtil() { }

    public static String distanceToString(double meter) {

        if(meter < 2000) {
            return Math.round(meter / 10.0) * 10 + " m";
        }
        else {
            return Math.round(meter / 100) / 10.0 + " km";
        }
    }
}
