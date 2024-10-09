package com.swp391.koibe.services.auctionkoi;

import com.swp391.koibe.dtos.auctionkoi.AuctionKoiDTO;
import com.swp391.koibe.dtos.auctionkoi.UpdateAuctionKoiDTO;
import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.AuctionKoi;
import com.swp391.koibe.responses.AuctionKoiResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAuctionKoiService {
    AuctionKoi createAuctionKoi(AuctionKoiDTO auctionKoiDTO) throws DataNotFoundException;
    AuctionKoi getAuctionKoiById(long id) throws DataNotFoundException;
    AuctionKoiResponse getAuctionKoiResponeById(long id) throws DataNotFoundException;
    List<AuctionKoiResponse> getAuctionKoiByAuctionId(long id);
    Page<AuctionKoiResponse> getAllAuctionKois(Pageable pageable);
    AuctionKoiResponse updateAuctionKoi(long auctionKoiId, UpdateAuctionKoiDTO updateAuctionKoiDTO);
    void deleteAuctionKoi(long id);
    boolean existsByName(String name);
    List<AuctionKoi> getAuctionIsSold();
    AuctionKoiResponse getAuctionKoiDetailsById(long id) throws DataNotFoundException;
    AuctionKoiResponse getAuctionKoiByAuctionIdAndKoiId(long aid, long id) throws DataNotFoundException;
}
