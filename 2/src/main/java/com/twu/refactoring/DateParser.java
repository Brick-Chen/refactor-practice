package com.twu.refactoring;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class DateParser {
    private static final String MINIMUM_CHARACTER_NUM = "%s string is less than %d characters";
    private static final String IS_NOT_INTEGER_MSG = "%s is not an integer";
    private static final String VALID_INTERVAL = "%s cannot be less than %d or more than %d";
    private final String dateAndTimeString;
    private static final HashMap<String, TimeZone> KNOWN_TIME_ZONES = new HashMap<String, TimeZone>();

    static {
        KNOWN_TIME_ZONES.put("UTC", TimeZone.getTimeZone("UTC"));
    }

    /**
     * Takes a date in ISO 8601 format and returns a date
     *
     * @param dateAndTimeString - should be in format ISO 8601 format
     *                          examples -
     *                          2012-06-17 is 17th June 2012 - 00:00 in UTC TimeZone
     *                          2012-06-17TZ is 17th June 2012 - 00:00 in UTC TimeZone
     *                          2012-06-17T15:00Z is 17th June 2012 - 15:00 in UTC TimeZone
     */
    public DateParser(String dateAndTimeString) {
        this.dateAndTimeString = dateAndTimeString;
    }

    public Date parse() {
        int year, month, date, hour, minute;

        year = getTime("Year", 0, 4,  2000, 2012);

        month = getTime("Month",5, 7,  1, 12);

        date = getTime("Date", 8, 10,  1, 31);

        if (dateAndTimeString.substring(11, 12).equals("Z")) {
            hour = 0;
            minute = 0;
        } else {
            hour = getTime("Hour", 11, 13, 0, 23);

            minute = getTime("Minute", 14, 16,  0, 59);

        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.set(year, month - 1, date, hour, minute, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private int getTime(String type, int start, int end, int minimum, int maximum) {
        int year;
        try {
            String yearString = dateAndTimeString.substring(start, end);
            year = Integer.parseInt(yearString);
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(String.format(MINIMUM_CHARACTER_NUM, type, end - start));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format(IS_NOT_INTEGER_MSG, type));
        }
        if (year < minimum || year > maximum)
            throw new IllegalArgumentException(String.format(VALID_INTERVAL, type, minimum, maximum));
        return year;
    }
}
