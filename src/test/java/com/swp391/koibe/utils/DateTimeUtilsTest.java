package com.swp391.koibe.utils;

import static org.junit.jupiter.api.Assertions.*;

import com.swp391.koibe.exceptions.MalformDataException;
import com.swp391.koibe.models.Auction;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class DateTimeUtilsTest {

    Auction existingAuction;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

    @BeforeEach
    void setup() {
        existingAuction = Auction.builder()
            .startTime(LocalDateTime.parse("2024-10-03 12:00:00.000000", formatter))
            .endTime(LocalDateTime.parse("2024-10-15 12:00:00.000000", formatter))
            .build();
    }

    @Test
    void isAuctionActive() {
        LocalDateTime bidTime = LocalDateTime.parse("2024-10-10 12:00:00.000000", formatter);
        assertTrue(DateTimeUtils.isAuctionActive(existingAuction.getStartTime(), existingAuction.getEndTime(), bidTime));
    }

    @Test
    void parseTime() {
        LocalDateTime expected = LocalDateTime.parse("2024-10-03 12:00:00.000000", formatter);
        assertEquals(expected, DateTimeUtils.parseTime("2024-10-03 12:00:00.000000"));
    }

    @Test
    void validateAuctionTimes() {
        assertThrows(MalformDataException.class, () -> {
            DateTimeUtils.validateAuctionTimes(
                existingAuction.getStartTime(),
                LocalDateTime.parse("2012-10-05 12:00:00.000000", formatter));
        });

        assertThrows(MalformDataException.class, () -> {
            DateTimeUtils.validateAuctionTimes(
                LocalDateTime.parse("2023-10-04 12:00:00.000000", formatter),
                existingAuction.getEndTime());
        });

        assertThrows(MalformDataException.class, () -> {
            DateTimeUtils.validateAuctionTimes(
                existingAuction.getStartTime(),
                LocalDateTime.parse("2024-10-02 12:00:00.000000", formatter));
        });
    }
}