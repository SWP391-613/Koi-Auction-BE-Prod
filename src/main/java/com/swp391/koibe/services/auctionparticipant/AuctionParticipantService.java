package com.swp391.koibe.services.auctionparticipant;

import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.Auction;
import com.swp391.koibe.models.AuctionParticipant;
import com.swp391.koibe.repositories.AuctionParticipantRepository;
import com.swp391.koibe.repositories.AuctionRepository;
import com.swp391.koibe.responses.UserResponse;
import com.swp391.koibe.utils.DTOConverter;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuctionParticipantService implements IAuctionParticipantService {
    private final AuctionParticipantRepository auctionParticipantRepository;
    private final AuctionRepository auctionRepository;

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

    @Override
    public Set<UserResponse> getAllUserJoinAuction(long auctionId) throws DataNotFoundException {

        Optional<Auction> existingAuction = auctionRepository.findById(auctionId);

        if (existingAuction.isEmpty()) {
            throw new DataNotFoundException("Auction not found");
        }

        return auctionParticipantRepository.getAuctionParticipantsByAuctionId(auctionId)
            .stream()
            .map(AuctionParticipant::getUser)
            .map(DTOConverter::convertToUserDTO)
            .collect(java.util.stream.Collectors.toSet());
    }
}
