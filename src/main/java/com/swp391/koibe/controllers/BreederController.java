package com.swp391.koibe.controllers;

import com.swp391.koibe.models.User;
import com.swp391.koibe.responses.pagination.UserPaginationResponse;
import com.swp391.koibe.responses.UserResponse;
import com.swp391.koibe.services.user.breeder.IBreederService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
            response.setItems(breeders.getContent()); // Set the list of UserResponse
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

}
