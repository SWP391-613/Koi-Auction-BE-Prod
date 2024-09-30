package com.swp391.koibe.services.koiimage;

import com.swp391.koibe.responses.KoiImageResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IKoiImageService {

    void createKoiImage(int koiId, String url) throws Exception;

    void updateKoiImage(int id, int koiId, String url) throws Exception;

    void deleteKoiImage(int id) throws Exception;

    void getKoiImage(int id) throws Exception;

    Page<KoiImageResponse> getAllKoiImages(Pageable pageable) throws Exception;
    List<KoiImageResponse> getKoiImagesByKoiId(Long koiId) throws Exception;

}
