package com.swp391.koibe.services.user.breeder;

import com.swp391.koibe.dtos.koi.KoiDTO;
import com.swp391.koibe.enums.EKoiStatus;
import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.User;
import com.swp391.koibe.responses.KoiResponse;
import com.swp391.koibe.responses.UserResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBreederService {

    Page<UserResponse> getAllBreeders(Pageable pageable);
    List<UserResponse> getAllBreeders();
    User findById(long breederId) throws DataNotFoundException;
    KoiResponse createKoi(Integer categoryId, KoiDTO koiDTO, long breederId);
    void updateBreeder(long breederId, User breeder);
    void updateKoi(long koiId, KoiDTO koiDTO);
    void deleteBreeder(long breederId);
    void deleteKoi(long koiId, long breederId);
    List<KoiResponse> getKoisByBreederID(long breederId);
    Page<KoiResponse> getKoisByBreederID(long breederId, Pageable pageable);
    Page<KoiResponse> getKoisByBreederIdAndStatus(long breederId, EKoiStatus koiStatus, Pageable pageable);
}
