package com.swp391.koibe.services.koi;

import com.swp391.koibe.dtos.KoiDTO;
import com.swp391.koibe.dtos.KoiImageDTO;
import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.Koi;
import com.swp391.koibe.models.KoiImage;
import com.swp391.koibe.responses.KoiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IKoiService<T> {

    Koi createKoi(KoiDTO koiDTO) throws DataNotFoundException;
    T getKoiById(long id) throws DataNotFoundException;
    Page<KoiResponse> getAllKois(Pageable pageable);
    KoiResponse updateKoi(long id, KoiDTO koiDTO);
    void deleteKoi(long id);
    boolean existsByName(String name);
    List<Koi> getAllKois();
    KoiImage createKoiImage(Long koiId, KoiImageDTO koiImageDTO)
        throws Exception;
}
