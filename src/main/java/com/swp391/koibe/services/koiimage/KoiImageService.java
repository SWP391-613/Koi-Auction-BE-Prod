package com.swp391.koibe.services.koiimage;

import com.swp391.koibe.models.Koi;
import com.swp391.koibe.models.KoiImage;
import com.swp391.koibe.repositories.KoiImageRepository;
import com.swp391.koibe.responses.KoiImageResponse;
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

    @Override
    public void createKoiImage(int koiId, String url) throws Exception {

    }

    @Override
    public void updateKoiImage(int id, int koiId, String url) throws Exception {

    }

    @Override
    public void deleteKoiImage(int id) throws Exception {

    }

    @Override
    public void getKoiImage(int id) throws Exception {

    }

    @Override
    public Page<KoiImageResponse> getAllKoiImages(Pageable pageable) throws Exception {
        Page<KoiImage> koiImages = koiImageRepository.findAll(pageable);
        return koiImages.map(DTOConverter::convertToKoiImageDTO);
    }

    @Override
    public List<KoiImageResponse> getKoiImagesByKoiId(Long koiId) throws Exception {
        List<KoiImage> koiImages = koiImageRepository.findByKoiId(koiId);
        return koiImages.stream().map(DTOConverter::convertToKoiImageDTO).toList();
    }
}
