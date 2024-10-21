package com.swp391.koibe.services.auction;

//import com.swp391.koibe.dtos.AuctionDTO;
import com.swp391.koibe.dtos.AuctionDTO;
import com.swp391.koibe.enums.EAuctionStatus;
import com.swp391.koibe.exceptions.base.DataAlreadyExistException;
import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.Auction;
import com.swp391.koibe.responses.AuctionResponse;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IAuctionService {

        Auction createAscendingAuction(AuctionDTO auctionDTO) throws DataAlreadyExistException;

        Auction createDescendingAuction(AuctionDTO auctionDTO) throws DataAlreadyExistException;

        Auction creatSealedAuction(AuctionDTO auctionDTO) throws DataAlreadyExistException;

        Auction createFixedPriceAuction(AuctionDTO auctionDTO) throws DataAlreadyExistException;

        AuctionResponse getById(long id) throws DataNotFoundException;

        Auction findAuctionById(long id) throws DataNotFoundException;

        Page<AuctionResponse> getAllAuctions(Pageable pageable);

        Auction update(long auctionId, AuctionDTO auctionDTO) throws DataNotFoundException;

        void delete(long id);

        List<Auction> getAllAuctions();

        List<Auction> getAuctionByStatus(EAuctionStatus status);
        Page<AuctionResponse> getAuctionByStatus(EAuctionStatus status, Pageable pageable);

        boolean updateAuctionStatus(Auction auction) throws DataNotFoundException;
        Set<Auction> getAuctionOnCondition(String condition);
        Set<Auction> getAuctionOnStatus(EAuctionStatus status);
        List<Auction> getAuctionByAuctioneerId(long auctioneerId) throws DataNotFoundException;
        Page<Auction> getAuctionByKeyword(String keyword, Pageable pageable);
}
