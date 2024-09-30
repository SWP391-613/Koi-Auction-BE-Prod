package com.swp391.koibe.utils;

import com.swp391.koibe.exceptions.MalformDataException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    /**
     * Checks if the bid time is within the auction period.
     * @param startTime The start time of the auction.
     * @param endTime The end time of the auction.
     * @param bidTime The time the bid was placed.
     * @return true if the auction is still active (not ended), false otherwise.
     */
    public static boolean isAuctionActive(LocalDateTime startTime, LocalDateTime endTime, LocalDateTime bidTime) {
        return bidTime.isAfter(startTime) && bidTime.isBefore(endTime);
    }

    public static LocalDateTime parseTime(String bidTime) {
        // Define the pattern matching your input string format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        return LocalDateTime.parse(bidTime, formatter);
    }

    public static void validateAuctionTimes(LocalDateTime startTime, LocalDateTime endTime) {
        // Check if start time is not in the past
        if (startTime.isBefore(LocalDateTime.now())) {
            throw new MalformDataException("Auction start time cannot be in the past");
        }

        // Check if start time is after end time
        if (startTime.isAfter(endTime)) {
            throw new MalformDataException("Auction start time cannot be after end time");
        }

        // Optional: Ensure the auction has a reasonable duration (at least 30 minutes, for example)
        if (startTime.plusDays(2).isAfter(endTime)) {
            throw new MalformDataException("Auction range must last at least 2 days");
        }
    }

}
