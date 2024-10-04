package com.swp391.koibe.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/prometheus")
public class PrometheusIntegrationApplication {

    @GetMapping("/something")
    public ResponseEntity<String> createLogs(){
        log.info("This is a log message");
        return ResponseEntity.ok("Log created");
    }

}
