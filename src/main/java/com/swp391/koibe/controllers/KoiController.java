package com.swp391.koibe.controllers;

import com.swp391.koibe.constants.ErrorMessage;
import com.swp391.koibe.dtos.KoiImageDTO;
import com.swp391.koibe.dtos.koi.KoiDTO;
import com.swp391.koibe.dtos.koi.UpdateKoiDTO;
import com.swp391.koibe.exceptions.MethodArgumentNotValidException;
import com.swp391.koibe.models.Koi;
import com.swp391.koibe.models.KoiImage;
import com.swp391.koibe.models.User;
import com.swp391.koibe.repositories.KoiRepository;
import com.swp391.koibe.responses.KoiResponse;
import com.swp391.koibe.responses.pagination.KoiPaginationResponse;
import com.swp391.koibe.services.koi.IKoiService;
import com.swp391.koibe.services.user.IUserService;
import com.swp391.koibe.utils.DTOConverter;
import jakarta.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequestMapping("${api.prefix}/kois")
@RestController
@RequiredArgsConstructor
public class KoiController {

    private final IKoiService<KoiResponse> koiService;
    private final KoiRepository koiRepository;
    private final IUserService userService;

//    @PostMapping("/generateFakeKois")
//    private ResponseEntity<String> generateFakeKois() {
//        Faker faker = new Faker();
//
//        SampleDataStorage.Koi koi = new SampleDataStorage.Koi();
//
//        for (int i = 1; i < 300; i++) {
//            String koiName = koi.getKoiNames().get(faker.number().numberBetween(0, koi.getKoiNames().size()));
////            if(koiService.existsByName(koiName)) {
////                continue;
////            }
//            Koi koiDTO = Koi.builder()
//                .name(koiName)
//                .price((float)faker.number().numberBetween(10, 90_000_000))
//                .EKoiStatus(koi.getKoiStatusList()[faker.number().numberBetween(0, koi.getKoiStatusList().length)])
//                .isDisplay(faker.number().numberBetween(0, 2))
//                .thumbnail("https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png")
//                .sex(koi.getGenders()[faker.number().numberBetween(0, koi.getGenders().length)])
//                .length(faker.number().numberBetween(1, 100))
//                .age(faker.number().numberBetween(1, 231)) //1-230
//                .description(faker.lorem().sentence())
//                .ownerId((long) faker.number().numberBetween(1, 4)) //userId from 1-3
//                .categoryId(faker.number().numberBetween(1, 11)) //1-10 categories
//                .build();
//            try{
//                koiService.createKoi(koiDTO);
//            } catch (Exception e) {
//                return ResponseEntity.badRequest().body(e.getMessage());
//            }
//        }
//        return ResponseEntity.ok("Fake kois generated");
//    }

    //pagination kois
    @GetMapping("") //kois/?page=0&limit=10
    public ResponseEntity<KoiPaginationResponse> getAllKois(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam("limit") int limit
    ) {

        KoiPaginationResponse response = new KoiPaginationResponse();

        try{
            PageRequest pageRequest = PageRequest.of(page, limit);
            Page<KoiResponse> kois = koiService.getAllKois(pageRequest);
            response.setItem(kois.getContent());
            response.setTotalPage(kois.getTotalPages());
            response.setTotalItem(kois.getTotalElements());
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<KoiResponse> getKoi(@PathVariable long id) {
        try{
            return ResponseEntity.ok(koiService.getKoiById(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping(value = "")
    @PreAuthorize("hasRole('ROLE_BREEDER')")
    public ResponseEntity<KoiResponse> createNewKoi(
        @Valid @RequestBody KoiDTO koiDTO,
        //@ModelAttribute("files") List<MultipartFile> files,
        //@RequestPart("file") MultipartFile file,
        @RequestHeader("Authorization") String authorizationHeader,
        BindingResult result
    ) {

        if(result.hasErrors()){
            throw new MethodArgumentNotValidException(result);
        }

        KoiResponse response = new KoiResponse();

        try {
            String extractedToken = authorizationHeader.substring(7); // Loại bỏ "Bearer " từ chuỗi token
            User breeder = userService.getUserDetailsFromToken(extractedToken);

            //need to save the product first to get the product id, get the id and add the image
            Koi newKoi = koiService.createKoi(koiDTO, breeder.getId());
            return ResponseEntity.ok(DTOConverter.convertToKoiDTO(newKoi));
        } catch (Exception e) {
            response.setMessage("Error creating new koi");
            response.setReason(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_BREEDER', 'ROLE_MANAGER', 'ROLE_STAFF')")
    public ResponseEntity<KoiResponse> updateProduct(
        @PathVariable("id") Long koiId,
        @Valid @RequestBody UpdateKoiDTO updateKoiDTO,
        BindingResult result
    ) {

        if(result.hasErrors()){
            throw new MethodArgumentNotValidException(result);
        }
        KoiResponse updatedKoi = new KoiResponse();
        try {
            updatedKoi = koiService.updateKoi(koiId, updateKoiDTO);
            return ResponseEntity.ok(updatedKoi);
        } catch (Exception e) {
            updatedKoi.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(updatedKoi);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_BREEDER', 'ROLE_MANAGER', 'ROLE_STAFF')")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long koiId) {
        try {
            koiService.deleteKoi(koiId);
            return ResponseEntity.ok().body("Koi deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_BREEDER')")
    public ResponseEntity<?> uploadImages(
        @PathVariable("id") Long koiId,
        @ModelAttribute("files") List<MultipartFile> files
    ) {
        try {
            KoiResponse existingKoi = koiService.getKoiById(koiId);
            files = files == null ? new ArrayList<>() : files;
            if (files.size() > KoiImage.MAXIMUM_IMAGES_PER_PRODUCT) {
                return ResponseEntity.badRequest().body(
                    ErrorMessage.MAXIMUM_IMAGES_PER_PRODUCT
                        + KoiImage.MAXIMUM_IMAGES_PER_PRODUCT
                        + " found: " + files.size());
            }
            List<KoiImage> koiImages = new ArrayList<>();
            for (MultipartFile file : files) {
                if (file.getSize() == 0) {
                    continue;
                }

                if (file.getSize() > 10 * 1024 * 1024) {
                    //throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE, "File size must be less than 10MB");
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                        .body("File size must be less than 10MB");
                }
                String contentType = file.getContentType(); //check if the file is image
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .body("File must be an image");
                }
                //Store file
                String fileName = storeFile(file);
                KoiImage koiImage = koiService.createKoiImage(
                    existingKoi.getId(),
                    KoiImageDTO.builder()
                        .imageUrl(fileName)
                        .build());
                //save to product_images later
                koiImages.add(koiImage);
            }
            return ResponseEntity.ok().body(koiImages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    private String storeFile(MultipartFile file) throws IOException {
        if (!isImageFile(file) || file.getOriginalFilename() == null) {
            throw new IOException("Invalid image format");
        }
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String uniqueFileName = UUID.randomUUID() + "_" + filename;
        Path uploadDir = Paths.get("uploads");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        //File.separator: depends on the OS, for windows it is '\', for linux it is '/'
        Path destination = Paths.get(uploadDir + File.separator + uniqueFileName);
        //copy the file to the destination
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }

}
