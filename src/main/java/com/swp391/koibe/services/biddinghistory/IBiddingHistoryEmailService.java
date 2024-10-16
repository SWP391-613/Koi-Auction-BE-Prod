package com.swp391.koibe.services.biddinghistory;

import com.swp391.koibe.models.AuctionKoi;
import jakarta.mail.MessagingException;
import org.thymeleaf.context.Context;

public interface IBiddingHistoryEmailService {
    void sendRefundEmail(
            AuctionKoi auctionKoi, String subject, String templateName, Context context
    ) throws MessagingException;
}
