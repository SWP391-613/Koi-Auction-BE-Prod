package com.swp391.koibe.services.koiimage;

import com.swp391.koibe.models.Koi;
import com.swp391.koibe.models.KoiImage;
import com.swp391.koibe.repositories.KoiImageRepository;
import com.swp391.koibe.responses.KoiImageResponse;
import com.swp391.koibe.services.koi.IKoiService;
import com.swp391.koibe.utils.DTOConverter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KoiImageService implements IKoiImageService {

    private final KoiImageRepository koiImageRepository;
    private final IKoiService koiService;

    @Override
    public void createKoiImage(long koiId, String url) throws Exception {
        Koi koi = (Koi) koiService.getKoiById(koiId);
        koi.setId(koiId);
        KoiImage koiImage = KoiImage.builder()
            .koi(koi)
            .imageUrl(url)
            .build();
        koiImageRepository.save(koiImage);
    }

    @Override
    public void updateKoiImage(long id, long koiId, String url) throws Exception {
        KoiImage koiImage = koiImageRepository.findById(id)
            .orElseThrow(() -> new Exception("Koi image not found"));
        koiImage.setImageUrl(url);
        koiImageRepository.save(koiImage);
    }

    @Override
    public void deleteKoiImage(long id) throws Exception {
        KoiImage koiImage = koiImageRepository.findById(id)
            .orElseThrow(() -> new Exception("Koi image not found"));
        koiImageRepository.delete(koiImage);
    }

    @Override
    public KoiImageResponse getKoiImage(long id) throws Exception {
        koiImageRepository.findById(id)
            .orElseThrow(() -> new Exception("Koi image not found"));
        return DTOConverter.convertToKoiImageDTO(koiImageRepository.getById(id));
    }

    @Override
    public Page<KoiImageResponse> getAllKoiImages(Pageable pageable) throws Exception {
        Page<KoiImage> koiImages = koiImageRepository.findAll(pageable);
        return koiImages.map(DTOConverter::convertToKoiImageDTO);
    }

    @Override
    public List<KoiImageResponse> getKoiImagesByKoiId(Long koiId) throws Exception {
        List<KoiImage> koiImages = koiImageRepository.findByKoiId(koiId);
            if (koiImages.isEmpty()) {
                throw new Exception("Koi images not found");
            }
        return koiImages.stream().map(DTOConverter::convertToKoiImageDTO).toList();

    }


}
