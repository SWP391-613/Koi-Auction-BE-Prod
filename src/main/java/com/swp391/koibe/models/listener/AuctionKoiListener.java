package com.swp391.koibe.models.listener;

import com.swp391.koibe.models.Auction;
import com.swp391.koibe.services.redis.auctionkoi.IAuctionKoiRedisService;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/*
Install Debezium and configure it to capture changes in the MySQL product table.

Set up a Kafka Connect destination to consume the Debezium change data events.

Implement a Spring Boot application that subscribes to the Kafka Connect destination and updates the Redis cache accordingly.
* */
@Component
@Slf4j
@RequiredArgsConstructor
public class AuctionKoiListener {

    private final IAuctionKoiRedisService auctionKoiRedisService;
    @PrePersist
    public void prePersist(Auction auction) {
        log.info("prePersist");
    }

    @PostPersist //save = persis
    public void postPersist(Auction auction) {
        // Update Redis cache
        log.info("postPersist");
        auctionKoiRedisService.clear();
    }

    @PreUpdate
    public void preUpdate(Auction auction) {
        //ApplicationEventPublisher.instance().publishEvent(event);
        log.info("preUpdate");
    }

    @PostUpdate
    public void postUpdate(Auction auction) {
        // Update Redis cache
        log.info("postUpdate");
        auctionKoiRedisService.clear();
    }

    @PreRemove
    public void preRemove(Auction auction) {
        //ApplicationEventPublisher.instance().publishEvent(event);
        log.info("preRemove");
    }

    @PostRemove
    public void postRemove(Auction auction) {
        // Update Redis cache
        log.info("postRemove");
        auctionKoiRedisService.clear();
    }
}
