package com.swp391.koibe.services.koi;

import com.swp391.koibe.constants.ErrorMessage;
import com.swp391.koibe.dtos.koi.KoiDTO;
import com.swp391.koibe.dtos.KoiImageDTO;
import com.swp391.koibe.dtos.koi.UpdateKoiDTO;
import com.swp391.koibe.dtos.koi.UpdateKoiStatusDTO;
import com.swp391.koibe.enums.EKoiStatus;
import com.swp391.koibe.exceptions.InvalidParamException;
import com.swp391.koibe.exceptions.base.DataAlreadyExistException;
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
import java.util.Set;
import java.util.stream.Collectors;
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
    public Koi createKoi(KoiDTO koiDTO, long breederId) throws Exception {
        //breeder cannot create other breeder's koi
        if(breederId != koiDTO.ownerId()){
            throw new InvalidParamException(ErrorMessage.BREEDER_CANNOT_CREATE_OTHER_BREEDER_KOI);
        }

        // check if koi already existed
        if (koiRepository.existsByName(koiDTO.name())) {
            throw new DataAlreadyExistException("Koi already existed: " + koiDTO.name());
        }

        //breeder create their own koi
        User existedUser = userRepository.findBreederById(koiDTO.ownerId())
            .orElseThrow(() ->
                             new DataNotFoundException("Breeder not found: " + koiDTO.ownerId()));

        Category existedCategory = categoryRepository.findById(koiDTO.categoryId())
            .orElseThrow(() ->
                             new DataNotFoundException("Category not found: " + koiDTO.categoryId()));

        Koi newKoi = Koi.builder()
            .name(koiDTO.name())
            .price(koiDTO.price())
            .status(EKoiStatus.UNVERIFIED) //default when create a new koi, breeder need to wait staff verify
            .isDisplay(0) //default when create a new koi, breeder need to wait staff verify then turn to 1
            .thumbnail(koiDTO.thumbnail())
            .sex(koiDTO.sex())
            .length(koiDTO.length())
            .age(koiDTO.age())
            .description(koiDTO.description() == null ? "Not provided" : koiDTO.description())
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
    public KoiResponse updateKoi(long id, UpdateKoiDTO koiDTO) {
        //find if koi exist
        Koi existingKoi = koiRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException("Koi not found: " + id));

        //find if koi owner exist
        User existingUser = userRepository.findById(koiDTO.ownerId())
            .orElseThrow(() -> new DataNotFoundException("User not found: " + koiDTO.ownerId()));

        // find if koi category exist
        Category existingCategory = categoryRepository.findById(koiDTO.categoryId())
            .orElseThrow(() -> new DataNotFoundException("Category not found: " + koiDTO.categoryId()));

        existingKoi.setName(koiDTO.name());
        existingKoi.setPrice(koiDTO.price());
        existingKoi.setStatus(EKoiStatus.valueOf(koiDTO.trackingStatus()));
        existingKoi.setIsDisplay(koiDTO.isDisplay());
        existingKoi.setThumbnail(koiDTO.thumbnail());
        existingKoi.setSex(koiDTO.sex());
        existingKoi.setLength(koiDTO.length());
        existingKoi.setAge(koiDTO.age());
        existingKoi.setDescription(koiDTO.description());
        existingKoi.setCategory(existingCategory);
        existingKoi.setOwner(existingUser);

        return DTOConverter.convertToKoiDTO(koiRepository.save(existingKoi));
    }

    @Override
    public void deleteKoi(long id) {
        //find if koi exist
        Koi existingKoi = koiRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException("Koi not found: " + id));
        koiRepository.delete(existingKoi);
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

    @Override
    public Set<KoiResponse> getKoiByStatus(EKoiStatus status) {
        return getAllKois().stream()
            .filter(koi -> koi.getStatus() == status)
            .map(DTOConverter::convertToKoiDTO)
            .collect(Collectors.toSet());
    }

    @Override
    public void updateKoiStatus(long id, UpdateKoiStatusDTO updateKoiStatusDTO) {
        //find if koi exist
        Koi existingKoi = koiRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException("Koi not found: " + id));
        existingKoi.setStatus(EKoiStatus.valueOf(updateKoiStatusDTO.trackingStatus()));
        koiRepository.save(existingKoi);
    }

}
