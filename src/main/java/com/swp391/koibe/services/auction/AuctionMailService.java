package com.swp391.koibe.services.auction;

import com.swp391.koibe.dtos.MailDTO;
import com.swp391.koibe.enums.EAuctionStatus;
import com.swp391.koibe.enums.EmailCategoriesEnum;
import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.Auction;
import com.swp391.koibe.models.User;
import com.swp391.koibe.responses.AuctionResponse;
import com.swp391.koibe.responses.UserResponse;
import com.swp391.koibe.services.auctionparticipant.IAuctionParticipantService;
import com.swp391.koibe.services.mail.IMailService;
import com.swp391.koibe.services.user.IUserService;
import com.swp391.koibe.utils.DTOConverter;
import jakarta.mail.MessagingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class AuctionMailService implements IAuctionMailService {

    private final IAuctionService auctionService;
    private final IAuctionParticipantService auctionParticipantService;
    private final IMailService mailService;
    private final IUserService userService;

    @Retryable(
        retryFor = {MessagingException.class},  // Retry only for specific exceptions
        maxAttempts = 3,                       // Maximum retry attempts
        backoff = @Backoff(delay = 2000)       // 2 seconds delay between retries
    )
    @Override
    public void sendAuctionClosedEmailToAllUser(long auctionId,
                                                String subject,
                                                String templateName,
                                                Context context) throws MessagingException {

        AuctionResponse existingAuction = auctionService.getById(auctionId);

        if (existingAuction == null) {
            throw new DataNotFoundException("Auction not found");
        }

        context.setVariable("auction_title", existingAuction.getTitle());
        context.setVariable("auction_end_date", existingAuction.getEndTime());
        context.setVariable("auction_url", "http://localhost:3000/auctions/" + auctionId);

        Set<UserResponse> userList = auctionParticipantService.getAllUserJoinAuction(auctionId);

        for (UserResponse user : userList) {
            // Send email to each user with personalized context
            sendEmailToUser(user, subject, templateName, context);
        }
    }

    @Retryable(
        retryFor = {MessagingException.class},  // Retry only for specific exceptions
        maxAttempts = 3,                       // Maximum retry attempts
        backoff = @Backoff(delay = 2000)       // 2 seconds delay between retries
    )
    // Scheduled method to notify all users about upcoming auctions
    // ****** : minute, hour, day of month, month, day of week
    @Scheduled(cron = "0 0 7 * * MON,WED,FRI") // Every Monday, Wednesday, and Friday at 7:00 AM
    @Override
    public void notifyUsersAboutUpcomingAuctions() throws MessagingException {
        Set<Auction> upcomingAuctions = auctionService.getAuctionOnStatus(EAuctionStatus.UPCOMING);
        Auction neerestAuction = upcomingAuctions.stream()
            .filter(auction -> auction.getStartTime().isAfter(
                LocalDateTime.now())).findFirst().orElse(null);

        if (neerestAuction == null) {
            return;
        }

        // because upcomming auction that mean the auction is not started yet not have any user join
        List<User> userList = userService.getAllUsers();

        // Fetch the users who have joined the auction
//            Set<UserResponse> userList = auctionParticipantService.getAllUserJoinAuction(auction.getId());

        // Create a Thymeleaf context for the email template
        Context context = new Context();
        context.setVariable("title", "Upcoming Auction Notification");
        context.setVariable("message", "An auction is coming soon!");
        context.setVariable("auction_title", neerestAuction.getTitle());
        context.setVariable("auction_start_date", neerestAuction.getStartTime().format(
            DateTimeFormatter.ISO_DATE_TIME));
        context.setVariable("auction_url",
                            "http://localhost:3000/auctions/" + neerestAuction.getId());

        // Notify all users about the upcoming auction
        for (User user : userList) {
            sendEmailToUser(DTOConverter.convertToUserDTO(user),
                            "Upcoming Auction Notification",
                            EmailCategoriesEnum.AUCTION_UPCOMING.getType(),
                            context);
        }

    }

    // Private helper method to avoid code duplication
    private void sendEmailToUser(UserResponse user, String subject, String templateName,
                                 Context baseContext) throws MessagingException {
        // Clone the original context by creating a new context
        Context userContext = new Context();

        // Set the base variables (manual copying of context variables)
        userContext.setVariable("auction_title", baseContext.getVariable("auction_title"));
        userContext.setVariable("auction_end_date", baseContext.getVariable("auction_end_date"));
        userContext.setVariable("auction_url", baseContext.getVariable("auction_url"));

        // Set user-specific variables
        String name = user.getFirstName();
        String to = user.getEmail();
        userContext.setVariable("name", name);

        // Send the email using the mail service
        mailService.sendMail(to, subject, templateName, userContext);
    }
}
