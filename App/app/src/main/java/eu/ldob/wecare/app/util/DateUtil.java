package eu.ldob.wecare.app.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private DateUtil() { }

    private static final String SHORT_DATE = "dd.MM.yy";
    private static final String LONG_TIME = "hh:mm:ss";

    public static String shortDate(Date date) {
        return new SimpleDateFormat(SHORT_DATE).format(date);
    }

    public static String longTime(Date date) {
        return new SimpleDateFormat(LONG_TIME).format(date);
    }
}
