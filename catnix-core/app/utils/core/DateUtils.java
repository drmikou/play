package utils.core;

import java.util.Date;

/**
 * @author Jean-Baptiste WATENBERG <jbwatenberg@juniorisep.com>
 */
public class DateUtils {
    private static DateUtils ourInstance = new DateUtils();

    public static DateUtils getInstance() {
        return ourInstance;
    }

    private DateUtils() {
    }

    public Date fromInt(int duration) {
        Date date = new Date();
        date.setTime(duration * 1000 - 3600000);
        return date;
    }

    public int fromDate(Date duration) {
        return (int) ((duration.getTime() + 3600000) / 1000);
    }
}
