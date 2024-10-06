package com.swp391.koibe.services.auctionparticipant;

import com.swp391.koibe.models.AuctionParticipant;

public interface  IAuctionParticipantService {
    AuctionParticipant createAuctionParticipant(AuctionParticipant auctionParticipant);
    AuctionParticipant getAuctionParticipantById(long id);
    AuctionParticipant updateAuctionParticipant(long id, AuctionParticipant auctionParticipant);
    void deleteAuctionParticipant(long id);
    Boolean hasJoinedAuction(long auctionId, long userId);
}
