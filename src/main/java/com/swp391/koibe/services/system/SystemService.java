package com.swp391.koibe.services.system;

import com.swp391.koibe.enums.EAuctionStatus;
import com.swp391.koibe.enums.EBidMethod;
import com.swp391.koibe.enums.OrderStatus;
import com.swp391.koibe.exceptions.SystemServiceTaskException;
import com.swp391.koibe.models.Auction;
import com.swp391.koibe.models.AuctionKoi;
import com.swp391.koibe.models.Bid;
import com.swp391.koibe.models.Order;
import com.swp391.koibe.services.auction.IAuctionMailService;
import com.swp391.koibe.services.auction.IAuctionService;
import com.swp391.koibe.services.auctionkoi.IAuctionKoiService;
import com.swp391.koibe.services.biddinghistory.IBiddingHistoryEmailService;
import com.swp391.koibe.services.biddinghistory.IBiddingHistoryService;
import com.swp391.koibe.services.order.IOrderService;
import com.swp391.koibe.services.otp.IOtpService;
import com.swp391.koibe.services.token.TokenService;
import com.swp391.koibe.services.user.IUserService;
import com.swp391.koibe.utils.FilterUtils;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class SystemService {

    private final IUserService userService;
    private final IBiddingHistoryService biddingHistoryService;
    private final IAuctionKoiService auctionKoiService;
    private final IAuctionService auctionService;
    private final IOrderService orderService;
    private final IAuctionMailService auctionMailService;
    private final IBiddingHistoryEmailService biddingHistoryEmailService;
    private final IOtpService otpService;
    private final TokenService tokenService;

    //every 5 minutes
    @Scheduled(cron = "0 */5 * * * *")
    @Async
    public void updateUpcomingAuctionStatus() {
        try {
            List<Auction> auctions = auctionService.getAuctionByStatus(EAuctionStatus.UPCOMING);
            for (Auction auction : auctions) {
                auctionService.updateAuctionStatus(auction);
            }
        } catch (SystemServiceTaskException e) {
            log.error("Error updating upcoming auction status", e.getCause());
        }
    }

    //every 5 minutes
    @Scheduled(cron = "0 */5 * * * *")
    @Async
    public void updateOnGoingAuctionStatus() {
        try {
            List<Auction> auctions = auctionService.getAuctionByStatus(EAuctionStatus.ONGOING);
            for (Auction auction : auctions) {
                boolean isUpdate = auctionService.updateAuctionStatus(auction);
                if (isUpdate) {
                    updateAuctionKoiStatus(auction);
                    Context context = new Context();
                    auctionMailService.sendAuctionClosedEmailToAllUser(
                            auction.getId(), "Auction Closed", "auctionClosed", context);

                }
            }
        } catch (SystemServiceTaskException e) {
            log.error("Error updating on going auction status", e.getCause());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void updateAuctionKoiStatus(@NotNull Auction auction) throws Exception {
        List<AuctionKoi> auctionKois = auctionKoiService.getAuctionKoiByAuctionIdV2(auction.getId());
        for (AuctionKoi auctionKoi : auctionKois) {
            boolean isUpdate = auctionKoiService.updateAuctionKoiStatus(auctionKoi.getId(), auctionKoi);
            if (isUpdate) {
                if (auctionKoi.isSold()) {
                    orderService.createOrderForAuctionKoi(auctionKoi, userService.getUserById(auctionKoi.getCurrentBidderId()));
                }
            }
            autoRefundUserBalanceAndCreateOrder(auctionKoi);
            biddingHistoryEmailService.sendRefundEmail(auctionKoi, "Balance Refund Email", "balanceRefunded", new Context());
        }
    }

    public void autoRefundUserBalanceAndCreateOrder(@NotNull AuctionKoi auctionKoi) throws Exception {
        Long winnerBidderId = auctionKoi.getCurrentBidderId();
        if (winnerBidderId != null && winnerBidderId != 0) {
            List<Bid> bids = biddingHistoryService.getBidsByAuctionKoiId(auctionKoi.getId());
            Map<Long, Long> userBids = new HashMap<>();
            // iterate through the Map userBids
            for (Map.Entry<Long, Long> entry :
                    FilterUtils.filterBiddersExceptWinner(
                            winnerBidderId,
                            bids,
                            userBids
                    ).entrySet()) {
                Long bidedAmount = biddingHistoryService.getBidderLatestBid(entry.getKey(), auctionKoi.getId()).getBidAmount();
                try {
                    userService.updateAccountBalance(entry.getKey(), bidedAmount);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    //every 10 minutes
    @Scheduled(cron = "0 */10 * * * *")
    @Async
    public void updateDescendingAuctionPrice() {
        try {
            List<Auction> auctions = auctionService.getAuctionByStatus(EAuctionStatus.ONGOING);
            for (Auction auction : auctions) {
                List<AuctionKoi> auctionKois = auctionKoiService.getAuctionKoiByAuctionIdV2(auction.getId()).stream().
                        filter(auctionKoi -> auctionKoi.getBidMethod().equals(EBidMethod.DESCENDING_BID)).
                        filter(auctionKoi -> !auctionKoi.isSold()).toList();
                for (AuctionKoi auctionKoi : auctionKois) {
                    auctionKoiService.updateDescendAuctionKoiPrice(auctionKoi.getId(), auctionKoi);
                }
            }
        } catch (SystemServiceTaskException e) {
            log.error("Error updating on going auction status", e.getCause());
        }
    }

    // every 10 minutes
    @Scheduled(cron = "0 */10 * * * *")
    @Async
    public void updateOrderStatus() {
        // with order status is PENDING, if it is not paid after 3 days, the order will be canceled
        try {
            List<Order> orders = orderService.getOrdersByStatus(OrderStatus.PENDING);
            for (Order order : orders) {
                if (order.getOrderDate().plusDays(3).isBefore(ChronoLocalDate.from(LocalDateTime.now()))) {
                    orderService.cancelOrder(order.getId());
                }
            }
        } catch (SystemServiceTaskException e) {
            log.error("Error updating order status", e.getCause());
        }

    }

    //every 5 minutes set the otp is expired
    @Scheduled(cron = "0 */5 * * * *")
    @Async
    public void setOtpExpired() {
        try {
            otpService.setOtpExpired();
        } catch (Exception e) {
            // Log the exception and handle error
            log.error("Error auto setting OTP expired", e.getCause());
        }
    }

    //validate the token every 15days
    @Scheduled(cron = "0 0 0 1/15 * ?")
    @Async
    public void setTokenExpired() {
        try {
            tokenService.setTokenExpired();
        } catch (Exception e) {
            // Log the exception and handle error
            log.error("Error auto setting Token expired", e.getCause());
        }
    }

    // every 10 minutes
    @Scheduled(cron = "0 */10 * * * *")
    @Async
    public void deleteOrder() {
        // with order status is CANCELLED, the order will be deleted after 10 days
        try {
            List<Order> orders = orderService.getOrdersByStatus(OrderStatus.CANCELLED);
            for (Order order : orders) {
                if (order.getOrderDate().plusDays(10).isBefore(ChronoLocalDate.from(LocalDateTime.now()))) {
                    orderService.deleteOrder(order.getId());
                }
            }
        } catch (SystemServiceTaskException e) {
            log.error("Error updating order status", e.getCause());
        }
    }

}