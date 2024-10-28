package com.swp391.koibe.controllers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AuctionKoiControllerTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAuctionKoisByAuctionId() {
    }

    @Test
    void getAuctionKoiByAuctionIdAndKoiId() {
    }

    @Test
    void getAllAuctions() {
    }

    @Test
    void getAuctionKoiDetails() {
    }

    @Test
    void createAuctionKoi() {
    }

    @Test
    void updateAuctionKoi() {
    }

    @Test
    void revokeKoiInAuction() {
    }

    @Test
    void deleteAuctionKoi() {
    }

    @Test
    void getOrdersByKeyword() {
    }
}