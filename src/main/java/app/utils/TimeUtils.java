package app.utils;

import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    public static Date addMinutes(Date date, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

}
