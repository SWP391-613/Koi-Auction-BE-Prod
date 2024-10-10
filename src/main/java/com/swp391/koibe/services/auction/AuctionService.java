package com.swp391.koibe.services.auction;

import com.swp391.koibe.dtos.AuctionDTO;
import com.swp391.koibe.enums.EAuctionStatus;
import com.swp391.koibe.exceptions.DeleteException;
import com.swp391.koibe.exceptions.MalformDataException;
import com.swp391.koibe.exceptions.base.DataAlreadyExistException;
import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.Auction;
import com.swp391.koibe.repositories.AuctionKoiRepository;
import com.swp391.koibe.repositories.AuctionParticipantRepository;
import com.swp391.koibe.repositories.AuctionRepository;
import com.swp391.koibe.responses.AuctionResponse;
import com.swp391.koibe.services.auctionparticipant.AuctionParticipantService;
import com.swp391.koibe.utils.DTOConverter;
import com.swp391.koibe.utils.DateTimeUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AuctionService implements IAuctionService {
    private final AuctionRepository auctionRepository;
    private final AuctionParticipantService auctionParticipantService;
    private final AuctionParticipantRepository auctionParticipantRepository;
    private final AuctionKoiRepository auctionKoiRepository;

    @Override
    public Auction createAscendingAuction(AuctionDTO auctionDTO) throws DataAlreadyExistException {
        LocalDateTime startTime = DateTimeUtils.parseTime(auctionDTO.startTime());
        LocalDateTime endTime = DateTimeUtils.parseTime(auctionDTO.endTime());
        DateTimeUtils.validateAuctionTimes(startTime, endTime);

        if (auctionDTO.statusName().equals(EAuctionStatus.ENDED.name())) {
            throw new MalformDataException("Cannot create ended auction");
        }

        // check if status name is valid
        boolean isValidStatus = Arrays.stream(EAuctionStatus.values())
                .anyMatch(status -> status.name().equals(auctionDTO.statusName()));

        if (!isValidStatus) {
            throw new MalformDataException("Invalid auction status name");
        }

        Auction newAuction = Auction.builder()
                .title(auctionDTO.title())
                .startTime(startTime)
                .endTime(endTime)
                .status(EAuctionStatus.valueOf(auctionDTO.statusName()))
                .build();

        return auctionRepository.save(newAuction);
    }

    @Override
    public Auction createDescendingAuction(AuctionDTO auctionDTO) throws DataAlreadyExistException {
        return null;
    }

    @Override
    public Auction creatSealedAuction(AuctionDTO auctionDTO) throws DataAlreadyExistException {
        return null;
    }

    @Override
    public Auction createFixedPriceAuction(AuctionDTO auctionDTO) throws DataAlreadyExistException {
        return null;
    }

    @Override
    public AuctionResponse getById(long id) throws DataNotFoundException {
        return auctionRepository.findById(id)
                .map(DTOConverter::convertToAuctionDTO)
                .orElseThrow(() -> new DataNotFoundException("Auction not found"));
    }

    @Override
    public Auction findAuctionById(long id) throws DataNotFoundException {
        return auctionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Auction not found"));
    }

    @Override
    public Page<AuctionResponse> getAllAuctions(Pageable pageable) {
        Page<Auction> auctions = auctionRepository.findAll(pageable);
        return auctions.map(DTOConverter::convertToAuctionDTO);
    }


    @Override
    public Auction update(long auctionId, AuctionDTO auctionDTO) throws DataNotFoundException {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new DataNotFoundException("Auction not found"));

        LocalDateTime startTime = DateTimeUtils.parseTime(auctionDTO.startTime());
        LocalDateTime endTime = DateTimeUtils.parseTime(auctionDTO.endTime());
        DateTimeUtils.validateAuctionTimes(startTime, endTime);

        // check if status name is valid
        boolean isValidStatus = Arrays.stream(EAuctionStatus.values())
                .anyMatch(status -> status.name().equals(auctionDTO.statusName()));

        if (!isValidStatus) {
            throw new MalformDataException("Invalid auction status name");
        }

        //can update the auction to any status

        auction.setTitle(auctionDTO.title());
        auction.setStartTime(startTime);
        auction.setEndTime(endTime);
        auction.setStatus(EAuctionStatus.valueOf(auctionDTO.statusName()));

        return auctionRepository.save(auction);
    }

    @Override
    public void delete(long id) {
        Auction auction = auctionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Auction not found"));

        //check if auction has joined participants
        if(!auctionParticipantRepository.getAuctionParticipantsByAuctionId(id).isEmpty()) {
            throw new DeleteException("Cannot delete auction with joined participants");
        }

        //check if auction has joined koi
         if(!auctionKoiRepository.getAuctionKoiByAuctionId(id).isEmpty()) {
             throw new DeleteException("Cannot delete auction with joined koi");
         }

         //only delete in case auction has no joined participants, koi added
        auctionRepository.delete(auction);
    }

    @Override
    public List<Auction> getAllAuctions() {
        return auctionRepository.findAll();
    }

    @Override
    public List<Auction> getAuctionByStatus(EAuctionStatus status) {
        return auctionRepository.findAllByStatus(status);
    }

    @Override
    public void updateAuctionStatus(long auctionId, Auction auction) throws DataNotFoundException {
        Auction auctionToUpdate = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new DataNotFoundException("Auction not found"));

        if (auctionToUpdate.getStatus().equals((EAuctionStatus.UPCOMING))) {
            if (auctionToUpdate.getStartTime().isBefore(LocalDateTime.now()) &&
                    auctionToUpdate.getEndTime().isAfter(LocalDateTime.now())) {
                auctionToUpdate.setStatus(EAuctionStatus.ONGOING);
            }
        }

        if (auctionToUpdate.getEndTime().isBefore(LocalDateTime.now()) &&
                auctionToUpdate.getStatus().equals(EAuctionStatus.ONGOING)) {
            auctionToUpdate.setStatus(EAuctionStatus.ENDED);
        }

        auctionRepository.save(auctionToUpdate);
    }
    public Set<Auction> getAuctionOnCondition(String condition) {
//        return switch (condition) {
//            case "ACTIVE" -> auctionRepository.getAuctionsByStartTimeAfter(LocalDateTime.now());
//            case "ONGOING" ->
//                auctionRepository.getAuctionsByStartTimeBeforeAndEndTimeAfter(LocalDateTime.now(),
//                                                                              LocalDateTime.now());
//            case "ENDED" -> auctionRepository.getAuctionsByEndTimeBefore(LocalDateTime.now());
//            default -> null;
//        };
        return null;
    }

    @Override
    public Set<Auction> getAuctionOnStatus(EAuctionStatus status) {
        return auctionRepository.getAuctionsByStatus(status);
    }
}
