package ru.pvolan.tools.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class CalendarHelper
{

    public static final String ISOpatternDefault = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";


    public static String toNiceString(Calendar c){
        return format(c, "yyyy-MM-dd HH:mm:ss Z");
    }


    public static String format(Calendar c, String format, TimeZone timeZone){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        simpleDateFormat.setCalendar(c);
        simpleDateFormat.setTimeZone(timeZone);
        return simpleDateFormat.format(c.getTime());
    }


    public static String format(Calendar c, String format){
        return format(c, format, TimeZone.getDefault());
    }


    public static Calendar parseFormat(String str, String format) throws ParseException {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat isoFormat = new SimpleDateFormat(format);
        c.setTime(isoFormat.parse(str));
        return c;
    }

}
