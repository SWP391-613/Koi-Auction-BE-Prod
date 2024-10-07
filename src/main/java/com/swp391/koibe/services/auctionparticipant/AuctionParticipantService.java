package com.swp391.koibe.services.auctionparticipant;

import com.swp391.koibe.models.AuctionParticipant;
import com.swp391.koibe.repositories.AuctionParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuctionParticipantService implements IAuctionParticipantService {
    private final AuctionParticipantRepository auctionParticipantRepository;

    @Override
    public AuctionParticipant createAuctionParticipant(AuctionParticipant auctionParticipant) {
        return auctionParticipantRepository.save(auctionParticipant);
    }

    @Override
    public AuctionParticipant getAuctionParticipantById(long id) {
        return auctionParticipantRepository.findById(id).orElse(null);
    }

    @Override
    public AuctionParticipant updateAuctionParticipant(long id, AuctionParticipant auctionParticipant) {
        return auctionParticipantRepository.save(auctionParticipant);
    }

    @Override
    public void deleteAuctionParticipant(long id) {
        auctionParticipantRepository.deleteById(id);
    }

    @Override
    public Boolean hasJoinedAuction(long auctionId, long userId) {
        try {
            return auctionParticipantRepository.getAuctionParticipantByAuctionIdAndUserId(auctionId, userId) != null;
        } catch (Exception e) {
            System.out.println("loi vcl: "+e.getMessage());
            return false;
        }
    }
}
