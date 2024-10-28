package com.swp391.koibe.controllers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class PaymentControllerTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void createDepositPayment() {
    }

    @Test
    void handleVNPayReturn() {
    }

    @Test
    void createOrderPayment() {
    }

    @Test
    void createDrawOutRequest() {
    }

    @Test
    void getPaymentByUserIdAndStatus() {
    }

    @Test
    void getPaymentsByStatusAndKeyword() {
    }

    @Test
    void updatePaymentStatus() {
    }
}