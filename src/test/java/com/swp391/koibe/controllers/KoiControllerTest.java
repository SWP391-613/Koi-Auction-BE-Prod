package com.swp391.koibe.controllers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class KoiControllerTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAllKois() {
    }

    @Test
    void getKoi() {
    }

    @Test
    void getKoiListByStatus() {
    }

    @Test
    void getBreederKoiListByStatus() {
    }

    @Test
    void getKoisByKeyword() {
    }

    @Test
    void getKoisByKeywordNotAuth() {
    }

    @Test
    void getAllKoisByKeyword() {
    }

    @Test
    void getUnverifiedKoisByKeyword() {
    }

    @Test
    void createNewKoi() {
    }

    @Test
    void updateKoiStatus() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProduct() {
    }

    @Test
    void uploadImages() {
    }
}