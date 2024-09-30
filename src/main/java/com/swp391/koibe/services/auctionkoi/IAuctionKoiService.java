package com.swp391.koibe.services.auctionkoi;

import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.AuctionKoi;
import com.swp391.koibe.responses.AuctionKoiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IAuctionKoiService {
    AuctionKoi createAuctionKoi(AuctionKoi auctionKoi) throws DataNotFoundException;
    AuctionKoi getAuctionKoiById(long id) throws DataNotFoundException;
    AuctionKoiResponse getAuctionKoiResponeById(long id) throws DataNotFoundException;
    List<AuctionKoiResponse> getAuctionKoiByAuctionId(long id);
    Page<AuctionKoiResponse> getAllAuctionKois(Pageable pageable);
    AuctionKoi updateAuctionKoi(long id, AuctionKoi auctionKoi);
    void deleteAuctionKoi(long id);
    boolean existsByName(String name);
    List<AuctionKoi> getAuctionIsSold();
    AuctionKoiResponse getAuctionKoiDetailsById(long id) throws DataNotFoundException;
    AuctionKoiResponse getAuctionKoiByAuctionIdAndKoiId(long aid, long id) throws DataNotFoundException;
}
