package com.swp391.koibe.controllers;

import com.swp391.koibe.dtos.koi.KoiDTO;
import com.swp391.koibe.exceptions.MethodArgumentNotValidException;
import com.swp391.koibe.models.User;
import com.swp391.koibe.responses.KoiResponse;
import com.swp391.koibe.responses.pagination.KoiPaginationResponse;
import com.swp391.koibe.responses.pagination.UserPaginationResponse;
import com.swp391.koibe.responses.UserResponse;
import com.swp391.koibe.services.user.breeder.IBreederService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/breeders")
public class BreederController {

    private final IBreederService breederService;

    @GetMapping("")
    public ResponseEntity<UserPaginationResponse> getAllBreeders(
        @RequestParam("page") int page,
        @RequestParam("limit") int limit
    ) {
        try {
            PageRequest pageRequest = PageRequest.of(page, limit);
            Page<UserResponse> breeders = breederService.getAllBreeders(pageRequest);

            // Create a UserPaginationResponse object and set its properties
            UserPaginationResponse response = new UserPaginationResponse();
            response.setItem(breeders.getContent()); // Set the list of UserResponse
            response.setTotalPage(breeders.getTotalPages()); // Set the total pages
            response.setTotalItem(breeders.getTotalElements()); // Set the total items
//            response.setPerPage(limit); // Set the current page

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAllBreeders() {
        try {
            List<UserResponse> breeders = breederService.getAllBreeders();
            return ResponseEntity.ok(breeders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBreeder(@PathVariable long id) {
        try {
            User breeder = breederService.findById(id);
            return ResponseEntity.ok(breeder);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/kois")
    public ResponseEntity<KoiPaginationResponse> getKoisByBreederId(
        @RequestParam("breeder_id") long breeder_id,
        @RequestParam("page") int page,
        @RequestParam("limit") int limit) {

        KoiPaginationResponse response = new KoiPaginationResponse();

        try {
            PageRequest pageRequest = PageRequest.of(page, limit);
            Page<KoiResponse> koiList = breederService.getKoisByBreederID(breeder_id, pageRequest);
            response.setItem(koiList.getContent());
            response.setTotalPage(koiList.getTotalPages());
            response.setTotalItem(koiList.getTotalElements());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/kois")
    @PreAuthorize("hasRole('ROLE_BREEDER')")
    public ResponseEntity<KoiResponse> createKoi(
        @RequestParam("category_id") Integer categoryId,
        @RequestParam("breeder_id") long breederId,
        @Valid @RequestBody KoiDTO koiDTO,
        BindingResult result
    ) {

        if(result.hasErrors()){
            throw new MethodArgumentNotValidException(result);
        }

        try {
            KoiResponse koi = breederService.createKoi(categoryId, koiDTO, breederId);
            return ResponseEntity.ok(koi);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

//    @PutMapping("/kois/{id}")
//    public ResponseEntity<KoiResponse> updateKoi(
//        @PathVariable("id") long id,
//        @Valid @RequestBody KoiDTO koiDTO,
//        BindingResult result
//    ) {
//
//        if(result.hasErrors()){
//            throw new MethodArgumentNotValidException(result);
//        }
//
//        try {
//            KoiResponse koi = breederService.updateKoi(id, koiDTO);
//            return ResponseEntity.ok(koi);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(null);
//        }
//    }


    @DeleteMapping("/{breeder_id}/koi/{id}")
    public ResponseEntity<?> deleteKoi(
        @PathVariable("id") long id,
        @PathVariable("breeder_id") long breederId
    ) {
        try {
            breederService.deleteKoi(id, breederId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
