package com.swp391.koibe.services.koi;

import com.swp391.koibe.constants.ErrorMessage;
import com.swp391.koibe.dtos.KoiDTO;
import com.swp391.koibe.dtos.KoiImageDTO;
import com.swp391.koibe.exceptions.InvalidParamException;
import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.Category;
import com.swp391.koibe.models.Koi;
import com.swp391.koibe.models.KoiImage;
import com.swp391.koibe.models.User;
import com.swp391.koibe.repositories.CategoryRepository;
import com.swp391.koibe.repositories.KoiImageRepository;
import com.swp391.koibe.repositories.KoiRepository;
import com.swp391.koibe.repositories.UserRepository;
import com.swp391.koibe.responses.KoiResponse;
import com.swp391.koibe.utils.DTOConverter;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KoiService implements IKoiService<KoiResponse> {

    private final KoiRepository koiRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final KoiImageRepository koiImageRepository;

    @Override
    public Koi createKoi(KoiDTO koiDTO) throws DataNotFoundException {

        User existedUser = userRepository.findById(koiDTO.ownerId())
            .orElseThrow(() ->
                             new DataNotFoundException("User not found: " + koiDTO.ownerId()));

        Category existedCategory = categoryRepository.findById(koiDTO.categoryId())
            .orElseThrow(() ->
                             new DataNotFoundException("Category not found: " + koiDTO.categoryId()));

        Koi newKoi = Koi.builder()
            .name(koiDTO.name())
            .price(koiDTO.price())
//            .trackingStatus(koiDTO.getKoiTrackingStatus())
            .isDisplay(koiDTO.isDisplay())
            .thumbnail(koiDTO.thumbnail())
            .sex(koiDTO.sex())
            .length(koiDTO.length())
            .age(koiDTO.age())
            .description(koiDTO.description())
            .owner(existedUser)
            .category(existedCategory)
            .build();
        return koiRepository.save(newKoi);
    }

    @Override
    public KoiResponse getKoiById(long id) throws DataNotFoundException {
        Optional<Koi> existingKoi = koiRepository.findById(id);
        if(existingKoi.isEmpty()){
            throw new DataNotFoundException("Koi not found: " + id);
        }
        return DTOConverter.convertToKoiDTO(existingKoi.get());
    }

    @Override
    public Page<KoiResponse> getAllKois(Pageable pageable) {
        Page<Koi> kois = koiRepository.findAll(pageable);
        return kois.map(DTOConverter::convertToKoiDTO);
    }

    @Override
    public Koi updateKoi(long id, KoiDTO koiDTO) {
        return null;
    }

    @Override
    public void deleteKoi(long id) {

    }

    @Override
    public boolean existsByName(String name) {
        return koiRepository.existsByName(name);
    }

    @Override
    public List<Koi> getAllKois() {
        return koiRepository.findAll();
    }

    @Override
    public KoiImage createKoiImage(Long koiId, KoiImageDTO koiImageDTO) throws Exception {
        Koi existingKoi = koiRepository
            .findById(koiId)
            .orElseThrow(() ->
                             new DataNotFoundException("Category not found: " + koiImageDTO.koiId()));

        KoiImage newKoiImage = KoiImage.builder()
            .koi(existingKoi)
            .imageUrl(koiImageDTO.imageUrl())
            .build();
        //khong cho insert qua 5 anh cho mot san pham
        int size = koiImageRepository.findByKoiId(koiId).size();
        if (size >= KoiImage.MAXIMUM_IMAGES_PER_PRODUCT) {
            throw new InvalidParamException(
                ErrorMessage.MAXIMUM_IMAGES_PER_PRODUCT
                    + KoiImage.MAXIMUM_IMAGES_PER_PRODUCT);
        }
        return koiImageRepository.save(newKoiImage);
    }
}
