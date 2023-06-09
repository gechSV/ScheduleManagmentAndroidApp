package ScheduleManagement.AndroidApp;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarConstructor {
    static public Calendar GetNewCalendar(){
        Calendar newCalendar = new GregorianCalendar();
        newCalendar.set(Calendar.YEAR, 0);
        newCalendar.set(Calendar.MONTH, 0);
        newCalendar.set(Calendar.DAY_OF_WEEK, 0);
        newCalendar.set(Calendar.HOUR_OF_DAY, 0);
        newCalendar.set(Calendar.MINUTE, 0);
        newCalendar.set(Calendar.MILLISECOND, 0);

        return newCalendar;
    }
}
