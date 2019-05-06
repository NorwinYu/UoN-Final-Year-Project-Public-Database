package tivoli.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Helps convert a date in String format to Date
 */
public class ParseDate {

    /**
     * Converts text to a Date object.
     * @param date the date to convert.
     * @return Date object representing the desired date.
     */
    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}
