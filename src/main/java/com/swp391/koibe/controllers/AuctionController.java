package com.swp391.koibe.controllers;

import com.github.javafaker.Faker;
import com.swp391.koibe.dtos.AuctionDTO;
import com.swp391.koibe.enums.EAuctionStatus;
import com.swp391.koibe.exceptions.GenerateDataException;
import com.swp391.koibe.exceptions.MalformDataException;
import com.swp391.koibe.exceptions.MethodArgumentNotValidException;
import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.Auction;
import com.swp391.koibe.responses.AuctionResponse;
import com.swp391.koibe.services.auction.IAuctionMailService;
import com.swp391.koibe.services.auction.IAuctionService;
import jakarta.validation.Valid;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequestMapping("${api.prefix}/auctions")
@RestController
@RequiredArgsConstructor
public class AuctionController {

    private final IAuctionService auctionService;
    private final IAuctionMailService auctionMailService;

    @PostMapping("/generateFakeAuctions")
    private void generateFakeAuctions() {
        Faker faker = new Faker();

        for (int i = 0; i < 100; i++) {
            LocalDateTime endTime = LocalDateTime.ofInstant(faker.date().past(30, TimeUnit.DAYS).toInstant(), ZoneId.systemDefault());
            LocalDateTime startTime = endTime.minusDays(faker.number().numberBetween(1, 20));

            AuctionDTO auction = AuctionDTO.builder()
                    .statusName(String.valueOf(EAuctionStatus.ENDED))
                    .endTime(String.valueOf(endTime))
                    .startTime(String.valueOf(startTime))
                    .title(" Auction #" + i)
                    .build();
            try {
                 auctionService.createAscendingAuction(auction);
            } catch (Exception e) {
                log.error("Error creating auction: " + e.getMessage());
                throw new GenerateDataException();
            }
        }
    }

    @GetMapping("/notify/upcoming")
    public ResponseEntity<?> notifyAllUserUpcomingAuction(){
        try{
            auctionMailService.notifyUsersAboutUpcomingAuctions();
            return ResponseEntity.ok("Success");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //pagination for auctions
    @GetMapping("") // /auctions/?page=1&limit=10
    public ResponseEntity<List<AuctionResponse>> getAllAuctions(
            @RequestParam int page,
            @RequestParam int limit) {
        try {
            PageRequest pageRequest = PageRequest.of(page, limit);
            Page<AuctionResponse> auctions = auctionService.getAllAuctions(pageRequest);
            return ResponseEntity.ok(auctions.getContent());
        }
        catch (Exception e) {
            log.error("Error getting all auctions: " + e.getMessage());
            throw new DataNotFoundException();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuctionResponse> getAuctionById(@PathVariable long id) {
        try {
            AuctionResponse auction = auctionService.getById(id);
            return ResponseEntity.ok(auction);
        }
        catch (Exception e) {
            log.error("Error getting auction by id: " + e.getMessage());
            throw new DataNotFoundException();
        }
    }

    @PostMapping("")
    public ResponseEntity<AuctionResponse> createAuction(
        @Valid @RequestBody AuctionDTO auctionDTO,
        BindingResult result
    ) {

        if(result.hasErrors()) {
            throw new MethodArgumentNotValidException(result);
        }

        AuctionResponse response = new AuctionResponse();
        try {
            Auction newAuction = auctionService.createAscendingAuction(auctionDTO);
            response.setId(newAuction.getId());
            response.setTitle(newAuction.getTitle());
            response.setStartTime(String.valueOf(newAuction.getStartTime()));
            response.setEndTime(String.valueOf(newAuction.getEndTime()));
            response.setStatus(newAuction.getStatus().getStatus());
            response.setAuctioneerId(newAuction.getAuctioneer().getId());
            return ResponseEntity.status(201).body(response);
        }
        catch (Exception e) {
            if(e instanceof MalformDataException){
                throw e;
            }
            log.error("Error creating auction: " + e.getMessage());
            response.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuctionResponse> updateAuction(
        @PathVariable long id,
        @Valid @RequestBody AuctionDTO auctionDTO,
        BindingResult result)
    {
        if(result.hasErrors()) {
            throw new MethodArgumentNotValidException(result);
        }

        AuctionResponse response = new AuctionResponse();

        try {
            Auction updatedAuction = auctionService.update(id, auctionDTO);
            response.setId(updatedAuction.getId());
            response.setTitle(updatedAuction.getTitle());
            response.setStartTime(String.valueOf(updatedAuction.getStartTime()));
            response.setEndTime(String.valueOf(updatedAuction.getEndTime()));
            response.setStatus(updatedAuction.getStatus().getStatus());
            response.setAuctioneerId(updatedAuction.getAuctioneer().getId());
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            log.error("Error updating auction: " + e.getMessage());
            response.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AuctionResponse> deleteAuction(@PathVariable long id) {
        AuctionResponse response = new AuctionResponse();
        try {
            auctionService.delete(id);
            return ResponseEntity.noContent().build();
        }
        catch (Exception e) {
            log.error("Error deleting auction: " + e.getMessage());
            response.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

}
