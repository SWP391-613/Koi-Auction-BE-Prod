package com.swp391.koibe.utils;

import com.swp391.koibe.models.*;
import com.swp391.koibe.responses.*;
import com.swp391.koibe.responses.order.OrderDetailResponse;
import com.swp391.koibe.responses.order.OrderResponse;
import java.time.format.DateTimeFormatter;

public class DTOConverter {

    public static UserResponse convertToUserDTO(User user) {
        return UserResponse.builder()
            .id(user.getId())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .phoneNumber(user.getPhoneNumber())
            .email(user.getEmail())
            .address(user.getAddress())
            .password(user.getPassword())
            .isActive(user.isActive() ? 1 : 0)
            .isSubscription(user.isSubscription() ? 1 : 0)
            .statusName(user.getStatus() != null ? user.getStatus().getStatus() : null)
            .dob(String.valueOf(user.getDob()).split(" ")[0])
            .avatarUrl(user.getAvatarUrl())
            .googleAccountId(user.getGoogleAccountId())
            .roleName(user.getRole() != null ? user.getRole().getName() : null)
            .accountBalance(user.getAccountBalance())
            .createdAt(user.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME))
            .updatedAt(user.getUpdatedAt().format(DateTimeFormatter.ISO_DATE_TIME))
            .build();
    }

    public static KoiResponse convertToKoiDTO(Koi koi) {
        return KoiResponse.builder()
            .id(koi.getId())
            .name(koi.getName())
            .sex(koi.getSex())
            .length(koi.getLength())
            .age(koi.getAge())
            .price(koi.getPrice())
            .statusName(koi.getStatus() != null ? koi.getStatus().getStatus() : null)
            .idDisplay(koi.getIsDisplay())
            .thumbnail(koi.getThumbnail())
            .description(koi.getDescription())
            .ownerId(koi.getOwner().getId())
            .categoryId(koi.getCategory().getId())
            .build();
    }

    public static KoiImageResponse convertToKoiImageDTO(KoiImage koiImage) {
        return KoiImageResponse.builder()
            .id(koiImage.getId())
            .koiId(koiImage.getKoi().getId())
            .imageUrl(koiImage.getImageUrl())
            .build();
    }

    public static AuctionResponse convertToAuctionDTO(Auction auction) {
        return AuctionResponse.builder()
            .id(auction.getId())
            .title(auction.getTitle())
            .startTime(String.valueOf(auction.getStartTime()))
            .endTime(String.valueOf(auction.getEndTime()))
            .status(auction.getStatus() != null ? auction.getStatus().getStatus() : null)
            .auctioneerId(auction.getAuctioneer().getId())
            .build();
    }

    public static AuctionKoiResponse convertToAuctionKoiDTO(AuctionKoi auctionKoi) {
        return AuctionKoiResponse.builder()
            .id(auctionKoi.getId())
            .auctionId(auctionKoi.getAuction().getId())
            .koiId(auctionKoi.getKoi().getId())
            .basePrice(auctionKoi.getBasePrice())
            .bidStep(auctionKoi.getBidStep())
            .bidMethod(auctionKoi.getBidMethod() != null ? String.valueOf(auctionKoi.getBidMethod()) : null)
            .currentBid(auctionKoi.getCurrentBid() == null ? 0 : auctionKoi.getCurrentBid())
            .currentBidderId(auctionKoi.getCurrentBidderId() == null ? 0 : auctionKoi.getCurrentBidderId())
            .isSold(auctionKoi.isSold())
            .build();
    }

    public static BidResponse convertToBidDTO(Bid bid) {
        return BidResponse.builder()
            .auctionKoiId(bid.getAuctionKoi().getId())
            .bidderId(bid.getBidder().getId())
            .bidAmount(bid.getBidAmount())
            .bidTime(bid.getBidTime().toString())
            .bidderName(String.format("%s %s", bid.getBidder().getFirstName(),bid.getBidder().getLastName()))
            .build();
    }

    public static OrderResponse fromOrder(Order order) {
        return OrderResponse
            .builder()
            .id(order.getId())
            .user(DTOConverter.convertToUserDTO(order.getUser()))
            .firstName(order.getFirstName())
            .lastName(order.getLastName())
            .phoneNumber(order.getPhoneNumber())
            .email(order.getEmail())
            .address(order.getAddress())
            .note(order.getNote())
            .orderDate(order.getOrderDate())
            .status(String.valueOf(order.getStatus()))
            .totalMoney(order.getTotalMoney())
            .shippingMethod(order.getShippingMethod())
            .shippingAddress(order.getShippingAddress())
            .shippingDate(order.getShippingDate())
            .paymentMethod(order.getPaymentMethod())
            .orderDetails(order.getOrderDetails())
            .build();
    }

    public static OrderDetailResponse fromOrderDetail(OrderDetail orderDetail) {
        return OrderDetailResponse
            .builder()
            .id(orderDetail.getId())
            .orderId(orderDetail.getOrder().getId())
            .productId(DTOConverter.convertToKoiDTO(orderDetail.getKoi()))
            .price(orderDetail.getPrice())
            .numberOfProducts(orderDetail.getNumberOfProducts())
            .totalMoney(orderDetail.getTotalMoney())
            .build();
    }

    public static FeedbackResponse fromFeedback(Feedback feedback) {
        return FeedbackResponse.builder()
                .createAt(feedback.getCreatedAt())
                .rating(feedback.getRating())
                .content(feedback.getContent())
                .build();
    }

}
