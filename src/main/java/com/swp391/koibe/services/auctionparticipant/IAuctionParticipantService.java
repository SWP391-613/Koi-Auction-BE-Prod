package com.swp391.koibe.services.auctionparticipant;

import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.AuctionParticipant;
import com.swp391.koibe.responses.AuctionResponse;
import com.swp391.koibe.responses.UserResponse;
import java.util.Set;

public interface  IAuctionParticipantService {
    AuctionParticipant createAuctionParticipant(AuctionParticipant auctionParticipant);
    AuctionParticipant getAuctionParticipantById(long id);
    AuctionParticipant updateAuctionParticipant(long id, AuctionParticipant auctionParticipant);
    void deleteAuctionParticipant(long id);
    Boolean hasJoinedAuction(long auctionId, long userId);
    Set<UserResponse> getAllUserJoinAuction(long auctionId) throws DataNotFoundException; // get all user who are bidding in a specific auction
}
