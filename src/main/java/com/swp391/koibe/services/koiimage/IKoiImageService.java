package com.swp391.koibe.services.koiimage;

import com.swp391.koibe.responses.KoiImageResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IKoiImageService {

    void createKoiImage(long koiId, String url) throws Exception;

    void updateKoiImage(long id, long koiId, String url) throws Exception;

    void deleteKoiImage(long id) throws Exception;

    KoiImageResponse getKoiImage(long id) throws Exception;

    Page<KoiImageResponse> getAllKoiImages(Pageable pageable) throws Exception;
    List<KoiImageResponse> getKoiImagesByKoiId(Long koiId) throws Exception;

}
