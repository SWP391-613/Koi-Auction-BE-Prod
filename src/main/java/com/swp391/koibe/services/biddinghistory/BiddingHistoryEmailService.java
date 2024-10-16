package com.swp391.koibe.services.biddinghistory;

import com.swp391.koibe.models.AuctionKoi;
import com.swp391.koibe.models.Bid;
import com.swp391.koibe.models.User;
import com.swp391.koibe.responses.UserResponse;
import com.swp391.koibe.services.mail.IMailService;
import com.swp391.koibe.services.user.UserService;
import com.swp391.koibe.utils.FilterUtils;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class BiddingHistoryEmailService implements IBiddingHistoryEmailService {

    private final IMailService mailService;
    private final IBiddingHistoryService biddingHistoryService;
    private final UserService userService;

    @Retryable(
            retryFor = {MessagingException.class},  // Retry only for specific exceptions
            maxAttempts = 3,                       // Maximum retry attempts
            backoff = @Backoff(delay = 2000)       // 2 seconds delay between retries
    )
    public void sendRefundEmail(AuctionKoi auctionKoi, String subject, String templateName, Context context) throws MessagingException {
        // Get all users who have bidded on the auction, and not won
        Long winnerBidderId = auctionKoi.getCurrentBidderId();
        if (winnerBidderId != null) {
            List<Bid> bids = biddingHistoryService.getBidsByAuctionKoiId(auctionKoi.getId());
            Map<Long, Long> userBids = new HashMap<>();
            // iterate through the Map userBids
            for (Map.Entry<Long, Long> entry :
                FilterUtils.filterBiddersExceptWinner(
                    winnerBidderId,
                    bids,
                    userBids
                ).entrySet())
            {
                Long bidedAmount = biddingHistoryService.getBidderLatestBid(entry.getKey(), auctionKoi.getId()).getBidAmount();
                try {
                    User user = userService.getUserById(entry.getKey());
                    context.setVariable("name", user.getFirstName() + " " + user.getLastName());
                    context.setVariable("auction_title", auctionKoi.getAuction().getTitle());
                    context.setVariable("auction_end_date", auctionKoi.getAuction().getEndTime());
                    context.setVariable("user_bid", bidedAmount);
                    context.setVariable("auction_url", "http://localhost:3000/auctions/" + auctionKoi.getAuction().getId());
                    mailService.sendMail(user.getEmail(), subject, templateName, context);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
