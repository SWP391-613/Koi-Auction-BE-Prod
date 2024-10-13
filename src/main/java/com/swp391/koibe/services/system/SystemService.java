package com.swp391.koibe.services.system;

import com.swp391.koibe.enums.EAuctionStatus;
import com.swp391.koibe.enums.EBidMethod;
import com.swp391.koibe.exceptions.SystemServiceTaskException;
import com.swp391.koibe.models.Auction;
import com.swp391.koibe.models.AuctionKoi;
import com.swp391.koibe.models.Bid;
import com.swp391.koibe.models.Order;
import com.swp391.koibe.services.auction.IAuctionService;
import com.swp391.koibe.services.auctionkoi.IAuctionKoiService;
import com.swp391.koibe.services.biddinghistory.IBiddingHistoryService;
import com.swp391.koibe.services.order.IOrderService;
import com.swp391.koibe.services.user.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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

    //every 10 minutes
    @Scheduled(cron = "0 */10 * * * *")
    @Async
    public void updateUpcomingAuctionStatus() {
        try {
            List<Auction> auctions = auctionService.getAuctionByStatus(EAuctionStatus.UPCOMING);
            for (Auction auction : auctions) {
                auctionService.updateAuctionStatus(auction.getId(), auction);
            }
        } catch (SystemServiceTaskException e) {
            log.error("Error updating upcoming auction status", e.getCause());
        }
    }

    //every 10 minutes
    @Scheduled(cron = "0 */10 * * * *")
    @Async
    public void updateOnGoingAuctionStatus() {
        try {
            List<Auction> auctions = auctionService.getAuctionByStatus(EAuctionStatus.ONGOING);
            for (Auction auction : auctions) {
                boolean isUpdate = auctionService.updateAuctionStatus(auction.getId(), auction);
                if (isUpdate) {
                    updateAuctionKoiStatus(auction);
                }
            }
        } catch (SystemServiceTaskException e) {
            log.error("Error updating on going auction status", e.getCause());
        }
    }


    public void updateAuctionKoiStatus(@NotNull Auction auction) throws SystemServiceTaskException {
        List<AuctionKoi> auctionKois = auctionKoiService.getAuctionKoiByAuctionIdV2(auction.getId());
        for (AuctionKoi auctionKoi : auctionKois) {
            auctionKoiService.updateAuctionKoiStatus(auctionKoi.getId(), auctionKoi);
            autoRefundUserBalance(auctionKoi);
        }
    }

    public void autoRefundUserBalance(@NotNull AuctionKoi auctionKoi) throws SystemServiceTaskException {
        Long winnerBidderId = auctionKoi.getCurrentBidderId();
        if (winnerBidderId != null) {
            List<Bid> bids = biddingHistoryService.getBidsByAuctionKoiId(auctionKoi.getId());
            Map<Long, Long> userBids = new HashMap<>();
            // get list of bidders except the winner
            for (Bid bid : bids) {
                if (!bid.getBidder().getId().equals(winnerBidderId)) {
                    userBids.put(bid.getBidder().getId(), 0L);
                }
            }
            // iterate through the Map userBids
            for (Map.Entry<Long, Long> entry : userBids.entrySet()) {
                Long bidedAmount = biddingHistoryService.getBidderLatestBid(entry.getKey(), auctionKoi.getId()).getBidAmount();
                try {
                    userService.updateAccountBalance(entry.getKey(), bidedAmount);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    //every hour
    @Scheduled(cron = "0 0 * * * *")
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

    //every hour
    @Scheduled(cron = "0 0 * * * *")
    @Async
    public void updateOrderStatus() {
        // with order status is PENDING, if it is not paid after 3 days, the order will be canceled
        try {
            List<Order> orders = orderService.getOrdersByKeyword("PENDING", null).getContent();
            for (Order order : orders) {
                if (order.getOrderDate().plusDays(3).isBefore(ChronoLocalDate.from(LocalDateTime.now()))) {
                    orderService.deleteOrder(order.getId());
                }
            }
        } catch (SystemServiceTaskException e) {
            log.error("Error updating order status", e.getCause());
        }

    }
}