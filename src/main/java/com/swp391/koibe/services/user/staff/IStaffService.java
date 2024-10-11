package com.swp391.koibe.services.user.staff;

import com.swp391.koibe.dtos.UserDTO;
import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.User;
import com.swp391.koibe.responses.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IStaffService {

    Page<UserResponse> getAllStaffs(Pageable pageable);
    User findById(long id) throws DataNotFoundException;
    User updateMemberToStaff(long id);
    User update(long id, UserDTO staff);
    void delete(long id);

}
