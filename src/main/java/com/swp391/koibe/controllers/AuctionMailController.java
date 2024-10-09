package com.swp391.koibe.controllers;

import com.swp391.koibe.exceptions.InvalidApiPathVariableException;
import com.swp391.koibe.services.auction.AuctionService;
import com.swp391.koibe.services.auction.IAuctionMailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

@Slf4j
@RestController
@RequestMapping("${api.prefix}/auction-mail")
@RequiredArgsConstructor
public class AuctionMailController {

    private final IAuctionMailService auctionMailService;
    private final AuctionService auctionService;

    @GetMapping("/end/{auctionId}")
    public void sendAuctionClosedEmailToAllUser(
        @PathVariable
        long auctionId) throws MessagingException{

        if(auctionId <= 0 ){
            throw new InvalidApiPathVariableException("Invalid auction id");
        }

        try{
            Context context = new Context();
            auctionMailService.sendAuctionClosedEmailToAllUser(
                auctionId,
                "Auction Closed",
                "auctionClosed",
                context);

        }catch (Exception e){
            log.error("Failed to send mail auction closed to all users: {}", e.getMessage());
            throw new MessagingException("Failed to send mail auction closed to all users" + e.getMessage());
        }

    }


}
