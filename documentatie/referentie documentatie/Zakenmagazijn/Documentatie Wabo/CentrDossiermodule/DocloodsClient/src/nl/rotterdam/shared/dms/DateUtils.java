package nl.rotterdam.shared.dms;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

public class DateUtils {
    public final static String SEARCH_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
    public final static String PROFILE_DATE_FORMAT = "d-M-yyyy";
    

    public static Date toDate(String string, boolean isSearch) throws ParseException {
        if( string == null || string.length() == 0) return null;
        DateFormat format = new SimpleDateFormat(isSearch?SEARCH_DATE_FORMAT:PROFILE_DATE_FORMAT);
        return format.parse(string);
    }
    public static String fromDate(Date date, boolean isSearch) {
        if( date == null ) return null;
        DateFormat format = new SimpleDateFormat(isSearch?SEARCH_DATE_FORMAT:PROFILE_DATE_FORMAT);
        return format.format(date);
    }

}
