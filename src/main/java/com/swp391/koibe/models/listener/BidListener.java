package com.swp391.koibe.models.listener;

import com.swp391.koibe.models.Auction;
import com.swp391.koibe.services.redis.bid.IBidRedisService;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class BidListener {

    private final IBidRedisService bidRedisService;
    @PrePersist
    public void prePersist(Auction auction) {
        log.info("prePersist");
    }

    @PostPersist //save = persis
    public void postPersist(Auction auction) {
        // Update Redis cache
        log.info("postPersist");
        bidRedisService.clear();
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
        bidRedisService.clear();
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
        bidRedisService.clear();
    }
}
