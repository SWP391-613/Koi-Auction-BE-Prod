package com.swp391.koibe.services.koi;

import com.swp391.koibe.dtos.KoiImageDTO;
import com.swp391.koibe.dtos.koi.KoiDTO;
import com.swp391.koibe.dtos.koi.UpdateKoiDTO;
import com.swp391.koibe.dtos.koi.UpdateKoiStatusDTO;
import com.swp391.koibe.enums.EKoiStatus;
import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.Koi;
import com.swp391.koibe.models.KoiImage;
import com.swp391.koibe.responses.KoiResponse;
import com.swp391.koibe.responses.pagination.KoiPaginationResponse;
import jakarta.mail.MessagingException;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IKoiService<T> {

    Koi createKoi(KoiDTO koiDTO, long breederId) throws Exception;

    T getKoiById(long id) throws DataNotFoundException;

    Page<KoiResponse> getAllKois(Pageable pageable);

    KoiResponse updateKoi(long id, UpdateKoiDTO koiDTO);

    void deleteKoi(long id);

    boolean existsByName(String name);

    List<Koi> getAllKois();

    KoiImage createKoiImage(Long koiId, KoiImageDTO koiImageDTO)
        throws Exception;

    Page<KoiResponse> getKoiByStatus(Pageable pageable,EKoiStatus status);

    void updateKoiStatus(long id, UpdateKoiStatusDTO updateKoiStatusDTO) throws MessagingException;

    Page<Koi> findKoiByKeyword(String keyword, Pageable pageable);
}