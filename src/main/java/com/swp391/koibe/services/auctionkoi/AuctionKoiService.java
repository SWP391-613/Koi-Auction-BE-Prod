package com.swp391.koibe.services.auctionkoi;

import com.swp391.koibe.dtos.auctionkoi.AuctionKoiDTO;
import com.swp391.koibe.dtos.auctionkoi.UpdateAuctionKoiDTO;
import com.swp391.koibe.enums.EAuctionStatus;
import com.swp391.koibe.enums.EBidMethod;
import com.swp391.koibe.exceptions.MalformDataException;
import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.Auction;
import com.swp391.koibe.models.AuctionKoi;
import com.swp391.koibe.models.Koi;
import com.swp391.koibe.repositories.AuctionKoiRepository;
import com.swp391.koibe.repositories.AuctionRepository;
import com.swp391.koibe.repositories.KoiRepository;
import com.swp391.koibe.responses.AuctionKoiResponse;
import com.swp391.koibe.services.auction.AuctionService;
import com.swp391.koibe.utils.DTOConverter;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuctionKoiService implements IAuctionKoiService {

    private final AuctionKoiRepository auctionKoiRepository;
    private final AuctionRepository auctionRepository;
    private final KoiRepository koiRepository;
    private final AuctionService auctionService;

    @Override
    public AuctionKoi createAuctionKoi(AuctionKoiDTO auctionKoiDTO) throws DataNotFoundException {

        //check if the auction is already include this koi
        List<AuctionKoi> auctionKois = auctionKoiRepository.getAuctionKoiByAuctionId(auctionKoiDTO.auctionId());
        for(AuctionKoi auctionKoi : auctionKois){
            if(Objects.equals(auctionKoi.getKoi().getId(), auctionKoiDTO.koiId())){
                throw new MalformDataException("This koi is already in this auction");
            }
        }

        // check if auction exists
        auctionRepository.findById(auctionKoiDTO.auctionId())
                .orElseThrow(() -> new DataNotFoundException("Auction not found"));

        //check auction status is not ended
        Auction auction = auctionService.findAuctionById(auctionKoiDTO.auctionId());
        if(auction.getStatus() == EAuctionStatus.ENDED){
            throw new MalformDataException("Auction is ended");
        }

        // check if koi exists
        if(!koiRepository.existsById(auctionKoiDTO.koiId())){
            throw new DataNotFoundException("Koi not found");
        }

        Optional<Koi> existingKoi = koiRepository.findById(auctionKoiDTO.koiId());
        if(existingKoi.isEmpty()){
            throw new DataNotFoundException("Koi not found");
        }

        // save auction koi
        AuctionKoi newAuctionKoi = AuctionKoi.builder()
            .basePrice(auctionKoiDTO.basePrice())
            .bidStep(auctionKoiDTO.bidStep())
            .bidMethod(EBidMethod.valueOf(auctionKoiDTO.bidMethod()))
            .isSold(false)
            .currentBid(0L)
            .currentBidderId(0L)
            .auction(auctionService.findAuctionById(auctionKoiDTO.auctionId()))
            .koi(existingKoi.get())
            .build();

        return auctionKoiRepository.save(newAuctionKoi);
    }

    @Override
    public AuctionKoi getAuctionKoiById(long id) throws DataNotFoundException {
        return auctionKoiRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Auction Koi not found"));
    }

    @Override
    public AuctionKoiResponse getAuctionKoiResponeById(long id) throws DataNotFoundException {
        AuctionKoi auctionKoi = auctionKoiRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Auction Koi not found"));
        return DTOConverter.convertToAuctionKoiDTO(auctionKoi);
    }

    @Override
    public List<AuctionKoiResponse> getAuctionKoiByAuctionId(long id) {
        return auctionKoiRepository.getAuctionKoiByAuctionId(id).stream().map(DTOConverter::convertToAuctionKoiDTO)
                .toList();
    }

    @Override
    public Page<AuctionKoiResponse> getAllAuctionKois(Pageable pageable) {
        Page<AuctionKoi> kois = auctionKoiRepository.findAll(pageable);
        return kois.map(DTOConverter::convertToAuctionKoiDTO);
    }

    @Override
    public AuctionKoi updateAuctionKoi(long auctionId, long koiId, UpdateAuctionKoiDTO updateAuctionKoiDTO) {
        return null;
    }

    @Override
    public void deleteAuctionKoi(long id) {
        auctionKoiRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Auction Koi not found"));
        auctionKoiRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return false;
    }

    @Override
    public List<AuctionKoi> getAuctionIsSold() {
        // get all auction koi
        return auctionKoiRepository.findAll().stream().filter(auctionKoi -> auctionKoi.isSold()).toList();
    }

    @Override
    public AuctionKoiResponse getAuctionKoiDetailsById(long id) throws DataNotFoundException {
        AuctionKoi auctionKoi = auctionKoiRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Auction Koi not found"));
        return DTOConverter.convertToAuctionKoiDTO(auctionKoi);
    }

    @Override
    public AuctionKoiResponse getAuctionKoiByAuctionIdAndKoiId(long aid, long id) throws DataNotFoundException {
        List<AuctionKoi> auctionKois = auctionKoiRepository.getAuctionKoiByAuctionId(aid);
        AuctionKoi auctionKoi = auctionKois.stream()
                .filter(auctionKoi1 -> auctionKoi1.getId() == id).findFirst()
                .orElseThrow(() -> new DataNotFoundException("Auction Koi not found"));
        return DTOConverter.convertToAuctionKoiDTO(auctionKoi);
    }
}
