package com.swp391.koibe.services.order;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class OrderMailServiceTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void sendOrderCreatedEmailToUser() {
    }

    @Test
    void sendOrderCancelledToUser() {
    }
}