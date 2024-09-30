package com.swp391.koibe.services.auctionkoi;

import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.AuctionKoi;
import com.swp391.koibe.repositories.AuctionKoiRepository;
import com.swp391.koibe.repositories.AuctionRepository;
import com.swp391.koibe.responses.AuctionKoiResponse;
import com.swp391.koibe.utils.DTOConverter;
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

    @Override
    public AuctionKoi createAuctionKoi(AuctionKoi auctionKoi) throws DataNotFoundException {
        return auctionKoiRepository.save(auctionKoi);
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
    public AuctionKoi updateAuctionKoi(long id, AuctionKoi auctionKoi) {
        return null;
    }

    @Override
    public void deleteAuctionKoi(long id) {

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
