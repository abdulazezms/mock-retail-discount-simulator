package com.myretailbusiness.discountservice.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class TimeUtils {

    /**
     * Calculates the number of years that have passed since the specified Unix epoch time.
     *
     * @param unixEpochTime The Unix epoch time in seconds since January 1, 1970.
     * @return The number of years as an integer.
     */
    public static int getYearsSinceEpoch(long unixEpochTime) {
        // Convert the Unix epoch time to an Instant
        Instant epochInstant = Instant.ofEpochSecond(unixEpochTime);

        // Get the current time as an Instant
        Instant nowInstant = Instant.now();

        // Convert both Instants to ZonedDateTime objects using the system default time zone
        ZonedDateTime epochDateTime = epochInstant.atZone(ZoneId.systemDefault());
        ZonedDateTime nowDateTime = nowInstant.atZone(ZoneId.systemDefault());

        // Calculate the difference in years between the two dates
        int yearsBetween = (int) ChronoUnit.YEARS.between(epochDateTime, nowDateTime);
        return yearsBetween;
    }
}
