package com.swp391.koibe.controllers;

import com.swp391.koibe.dtos.KoiDTO;
import com.swp391.koibe.dtos.auctionkoi.AuctionKoiDTO;
import com.swp391.koibe.dtos.auctionkoi.UpdateAuctionKoiDTO;
import com.swp391.koibe.enums.EBidMethod;
import com.swp391.koibe.enums.EKoiStatus;
import com.swp391.koibe.exceptions.GenerateDataException;
import com.swp391.koibe.exceptions.MethodArgumentNotValidException;
import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.Auction;
import com.swp391.koibe.models.AuctionKoi;
import com.swp391.koibe.models.Koi;
import com.swp391.koibe.responses.AuctionKoiResponse;
import com.swp391.koibe.services.auction.IAuctionService;
import com.swp391.koibe.services.auctionkoi.IAuctionKoiService;
import com.swp391.koibe.services.biddinghistory.IBiddingHistoryService;
import com.swp391.koibe.services.koi.IKoiService;
import com.swp391.koibe.services.redis.auctionkoi.AuctionKoiRedisService;
import com.swp391.koibe.services.user.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("${api.prefix}/auctionkois")
@RequiredArgsConstructor
public class AuctionKoiController {

    private final IAuctionKoiService auctionKoiService;
    private final IAuctionService auctionService;
    private final IKoiService koiService;
    private final IUserService userService;
    private final IBiddingHistoryService biddingHistoryService;
    private final AuctionKoiRedisService auctionKoiRedisService;

    @GetMapping("/auction/{id}")
    public ResponseEntity<List<AuctionKoiResponse>> getAuctionKoisByAuctionId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(auctionKoiService.getAuctionKoiByAuctionId(id));
        } catch (Exception e) {
            log.error("Error getting auctionkois by auction id: " + e.getMessage());
            throw new DataNotFoundException();
        }
    }

//    //localhost:8080/api/v1/auctionkois/auction?id=1
//    @GetMapping("/auction")
//    public ResponseEntity<AuctionKoiPaginationResponse> getDetailAuction(
//        @RequestParam(defaultValue = "") String keyword,
//        @RequestParam(defaultValue = "0", name = "bid_method") int bidMethod,
//        @RequestParam(defaultValue = "0") int page,
//        @RequestParam(defaultValue = "10") int limit
//    ) throws JsonProcessingException {
//        try {
//            int totalPages = 0;
//            //productRedisService.clear();
//            // Tạo Pageable từ thông tin trang và giới hạn
//            PageRequest pageRequest = PageRequest.of(
//                page, limit,
//                //Sort.by("createdAt").descending()
//                Sort.by("id").ascending()
//            );
//
//            List<AuctionKoiResponse> auctionKoiResponseList = auctionKoiRedisService
//                .getAllAuctionKois(keyword, bidMethod, pageRequest);
//            if(auctionKoiResponseList!=null && !auctionKoiResponseList.isEmpty()){
//                totalPages = auctionKoiResponseList.get(0).getTotalPages();
//            }
//
//            return ResponseEntity.ok(auctionKoiService.getAuctionKoiByAuctionId(id));
//        } catch (Exception e) {
//            log.error("Error getting auctionkois by auction id: " + e.getMessage());
//            throw new DataNotFoundException();
//        }
//    }

    @GetMapping("/{aid}/{id}")
    public ResponseEntity<?> getAuctionKoiByAuctionIdAndKoiId(@PathVariable Long aid, @PathVariable Long id) {
        try {
            return ResponseEntity.ok(auctionKoiService.getAuctionKoiByAuctionIdAndKoiId(aid, id));
        } catch (Exception e) {
            log.error("Error getting auctionkoi by auction id and koi id: " + e.getMessage());
            throw new DataNotFoundException("Error getting auctionkoi by auction id and koi id: " + e.getMessage());
        }
    }

//     @PostMapping("/generateFakeAuctionKois")
//     public ResponseEntity<String> generateFakeAuctionKois() throws
//     DataNotFoundException {
//     List<Auction> auctions = auctionService.getAllAuctions();
//     List<Koi> kois = koiService.getAllKois();
//     Set<Long> usedKoiIds = new HashSet<>();
//     Random random = new Random();
//
//     for (Auction auction : auctions) {
//     List<Koi> availableKois = new ArrayList<>(kois);
//     availableKois.removeIf(koi -> usedKoiIds.contains(koi.getId()));
//
//     int koiCount = Math.min(10, availableKois.size());
//     for (int i = 0; i < koiCount; i++) {
//     Koi randomKoi = availableKois.remove(random.nextInt(availableKois.size()));
//     usedKoiIds.add(randomKoi.getId());
//
//     boolean isSold = random.nextBoolean();
//     long bid = 0;
//     if (isSold) {
//     bid = randomBid(randomKoi.getPrice());
//     }
//
//     AuctionKoi auctionKoi = AuctionKoi.builder()
//     .auction(auction)
//     .koi(randomKoi)
//     .basePrice(randomKoi.getPrice())
//     .bidMethod(EBidMethod.ASCENDING_BID)
//     .currentBid(isSold ? bid : 0)
//     .currentBidderId(isSold ? random.nextLong(1, 20) : null)
//     .isSold(isSold)
//     .bidStep(isSold ? Math.floorDiv((bid - randomKoi.getPrice()), 10) : 0)
//     .build();
//
//     // active the koi when it is in auction
//     randomKoi.setStatus(EKoiStatus.VERIFIED);
//
//     if (isSold) {
//     randomKoi.setStatus(EKoiStatus.SOLD);
//     randomKoi.setOwner(userService.getUserById(auctionKoi.getCurrentBidderId()));
//     kois.remove(randomKoi);
//     }
//
//     // convert Koi to KoiDTO
//     KoiDTO randomKoiDTO = new KoiDTO(
//        randomKoi.getName(), randomKoi.getPrice(), randomKoi.getStatus().name(), randomKoi.getIsDisplay(),
//        randomKoi.getThumbnail(), randomKoi.getSex(), randomKoi.getLength(), randomKoi.getAge(),
//        randomKoi.getDescription(), randomKoi.getCategory().getId(), randomKoi.getOwner().getId()
//
//     );
//
//     try {
//     koiService.updateKoi(randomKoi.getId(), randomKoiDTO);
//     auctionKoiService.createAuctionKoiV2(auctionKoi);
//     } catch (Exception e) {
//     log.error("Error creating AuctionKoi or updating Koi: " + e.getMessage(), e);
//     throw new GenerateDataException("Error creating AuctionKoi or updating Koi: "
//     + e.getMessage());
//     }
//     }
//     }
//     return ResponseEntity.ok("Fake AuctionKoi data generated successfully");
//     }
//
//    private int randomBid(float basePrice) {
//        return (int) (basePrice * (1.5));
//    }

    @GetMapping("") // /auctions/?page=1&limit=10
    public ResponseEntity<List<AuctionKoiResponse>> getAllAuctions(
            @RequestParam int page,
            @RequestParam int limit) {
        try {
            PageRequest pageRequest = PageRequest.of(page, limit);
            Page<AuctionKoiResponse> auctionKois = auctionKoiService.getAllAuctionKois(pageRequest);
            return ResponseEntity.ok(auctionKois.getContent());
        } catch (Exception e) {
            log.error("Error getting all auctionkois    : " + e.getMessage());
            throw new DataNotFoundException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuctionKoiResponse> getAuctionKoiDetails(@PathVariable Long id) {
        try {
            AuctionKoiResponse auctionKoiResponse = auctionKoiService.getAuctionKoiDetailsById(id);
            return ResponseEntity.ok(auctionKoiResponse);
        } catch (DataNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error getting auctionkoi by id: " + e.getMessage());
            throw new DataNotFoundException("Error getting auctionkoi by id: " + e.getMessage());
        }
    }

    // assign koi list to an auction
    @PostMapping("")
    public ResponseEntity<AuctionKoiResponse> createAuctionKoi(
            @Valid @RequestBody AuctionKoiDTO auctionKoiDTO,
            BindingResult result) {

        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(result);
        }

        AuctionKoiResponse response = new AuctionKoiResponse();
        try {
            AuctionKoi newAuctionKoi = auctionKoiService.createAuctionKoi(auctionKoiDTO);
            response.setId(newAuctionKoi.getId());
            response.setBasePrice(newAuctionKoi.getBasePrice());
            response.setCeilPrice(newAuctionKoi.getCeilPrice());
            response.setBidMethod(String.valueOf(newAuctionKoi.getBidMethod()));
            response.setCurrentBid(newAuctionKoi.getCurrentBid());
            response.setCurrentBidderId(newAuctionKoi.getCurrentBidderId());
            response.setSold(newAuctionKoi.isSold());
            response.setKoiId(newAuctionKoi.getKoi().getId());
            response.setAuctionId(newAuctionKoi.getAuction().getId());
            response.setBidStep(newAuctionKoi.getBidStep());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error creating auctionkoi: " + e.getMessage());
            throw new GenerateDataException("Error creating auctionkoi: " + e.getMessage());
        }
    }

    @PutMapping("/auctionkois/{auctionkoi_id}")
    public ResponseEntity<AuctionKoiResponse> updateAuctionKoi(
            @PathVariable Long auctionkoi_id,
            @Valid @RequestBody UpdateAuctionKoiDTO updateAuctionKoiDTO,
            BindingResult result) {
        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(result);
        }
        try {
            AuctionKoiResponse updatedAuctionKoi = auctionKoiService.updateAuctionKoi(auctionkoi_id,
                    updateAuctionKoiDTO);
            return ResponseEntity.ok(updatedAuctionKoi);
        } catch (Exception e) {
            log.error("Error updating auctionkoi: " + e.getMessage());
            throw new GenerateDataException("Error updating auctionkoi: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuctionKoi(@PathVariable Long id) {
        try {
            auctionKoiService.deleteAuctionKoi(id);
            return ResponseEntity.ok("AuctionKoi deleted successfully");
        } catch (Exception e) {
            log.error("Error deleting auctionkoi: " + e.getMessage());
            throw new DataNotFoundException("Error deleting auctionkoi: " + e.getMessage());
        }
    }

//    @PostMapping("/auction/{auction_id}/koi/{koi_id}/bid")
//    public ResponseEntity<BidResponse> placeBid(
//            @PathVariable Long auction_id,
//            @PathVariable Long koi_id,
//            @Valid @RequestBody BidDTO bidRequest,
//            BindingResult result) throws Exception {
//        if (result.hasErrors()) {
//            throw new MethodArgumentNotValidException(result);
//        }
//        BidDTO bidDTO = BidDTO.builder()
//                .auctionKoiId(koi_id)
//                .bidAmount(bidRequest.bidAmount())
//                .bidTime(bidRequest.bidTime())
//                .bidderId(bidRequest.bidderId())
//                .build();
//        BidResponse response = biddingHistoryService.placeBid(bidDTO);
//        return ResponseEntity.ok(response);
//    }

}