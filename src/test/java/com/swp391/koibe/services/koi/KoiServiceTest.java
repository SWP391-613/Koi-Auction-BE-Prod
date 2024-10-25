package com.swp391.koibe.services.koi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class KoiServiceTest {

    @MockBean
    private KoiService koiService;

    @BeforeEach
    void setUp() {
        Mockito.reset(koiService);
    }

    @Test
    void createKoi() {
    }

    @Test
    void getKoiById() {
    }

    @Test
    void getAllKois() {
    }

    @Test
    void updateKoi() {
    }

    @Test
    void deleteKoi() {
    }

    @Test
    void existsByName() {
    }

    @Test
    void testGetAllKois() {
    }

    @Test
    void createKoiImage() {
    }
}