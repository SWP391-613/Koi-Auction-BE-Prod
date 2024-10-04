package com.swp391.koibe.utils;

import com.swp391.koibe.models.*;
import com.swp391.koibe.responses.*;

public class DTOConverter {

    public static UserResponse convertToUserDTO(User user) {
        return UserResponse.builder()
            .id(user.getId())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .phoneNumber(user.getPhoneNumber())
            .email(user.getEmail())
            .address(user.getAddress())
            //.password(user.getPassword())
            .isActive(user.isActive() ? 1 : 0)
            .isSubscription(user.isSubscription() ? 1 : 0)
            .statusName(user.getStatus() != null ? user.getStatus().getStatus() : null)
            .dob(user.getDob())
            .avatarUrl(user.getAvatarUrl())
            .googleAccountId(user.getGoogleAccountId())
            .roleName(user.getRole() != null ? user.getRole().getName() : null)
            .accountBalance(user.getAccountBalance())
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
            .videoUrl(koiImage.getVideoUrl())
            .build();
    }

    public static AuctionResponse convertToAuctionDTO(Auction auction) {
        return AuctionResponse.builder()
            .id(auction.getId())
            .title(auction.getTitle())
            .startTime(String.valueOf(auction.getStartTime()))
            .endTime(String.valueOf(auction.getEndTime()))
            .status(auction.getStatus() != null ? auction.getStatus().getStatus() : null)
            .build();
    }

    public static AuctionKoiResponse convertToAuctionKoiDTO(AuctionKoi auctionKoi) {
        return AuctionKoiResponse.builder()
            .id(auctionKoi.getId())
            .auctionId(auctionKoi.getAuction().getId())
            .koiId(auctionKoi.getKoi().getId())
            .basePrice(auctionKoi.getBasePrice())
            .bidStep(auctionKoi.getBidStep())
            .bidMethod(auctionKoi.getBidMethod() != null ? auctionKoi.getBidMethod().getType() : null)
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

}
