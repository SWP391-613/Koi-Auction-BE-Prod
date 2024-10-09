package com.swp391.koibe.services;

import com.swp391.koibe.models.AuctionKoi;
import com.swp391.koibe.models.Bid;
import com.swp391.koibe.models.User;

public interface Biddable {
    AuctionKoi ascending(AuctionKoi auctionKoi, User bidder, Bid bid) throws Exception;
    AuctionKoi descending(AuctionKoi auctionKoi, User bidder, Bid bid) throws Exception;
    AuctionKoi fixedPrice(AuctionKoi auctionKoi, User bidder, Bid bid) throws Exception;
    AuctionKoi sealed(AuctionKoi auctionKoi, User bidder, Bid bid) throws Exception;
}
