package com.swp391.koibe.services.auction;

import jakarta.mail.MessagingException;
import org.thymeleaf.context.Context;

public interface IAuctionMailService {
    void sendAuctionClosedEmailToAllUser(long auctionId,
                                         String subject,
                                         String templateName,
                                         Context context) throws MessagingException;
    void notifyUsersAboutUpcomingAuctions() throws MessagingException;
}
