package com.swp391.koibe.controllers;

import com.github.javafaker.Faker;
import com.swp391.koibe.repositories.KoiImageRepository;
import com.swp391.koibe.responses.KoiImageResponse;
import com.swp391.koibe.responses.pagination.KoiImagePaginationResponse;
import com.swp391.koibe.services.koiimage.IKoiImageService;
import com.swp391.koibe.utils.SampleDataStorage;
import com.swp391.koibe.utils.SampleDataStorage.KoiImage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/koiimage")
@RequiredArgsConstructor
public class KoiImageController {

    private final IKoiImageService koiImageService;
    private final KoiImageRepository koiImageRepository;

    @PostMapping("/generateFakeKoiImages")
    public ResponseEntity<?> generateFakeKoiImages() {
        Faker faker = new Faker();
        SampleDataStorage.KoiImage koiImage = new KoiImage();
        for(int i = 1; i <= 100; i++){

        }
        return ResponseEntity.ok("Fake koi images generated");
    }

    @GetMapping("")
    public ResponseEntity<?> getAllKoiImages(
        @RequestParam("page") int page,
        @RequestParam("limit") int limit
    ) {
        KoiImagePaginationResponse response =
            new KoiImagePaginationResponse();

        try{
            PageRequest pageRequest = PageRequest.of(page, limit);
            Page<KoiImageResponse> koiImages = koiImageService.getAllKoiImages(pageRequest);
            response.setItems(koiImages.getContent());
            response.setTotalPage(koiImages.getTotalPages());
            response.setTotalItem(koiImages.getTotalElements());
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getKoiImageByKoiId(
        @PathVariable("id") Long id
    ) {
        try{
            return ResponseEntity.ok(koiImageService.getKoiImagesByKoiId(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
