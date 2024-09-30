package com.swp391.koibe.services.user.staff;

import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.User;
import com.swp391.koibe.responses.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IStaffService {

    Page<UserResponse> getAllStaffs(Pageable pageable);
    User findById(long id) throws DataNotFoundException;
    User create(User staff);
    User update(long id, User staff);
    void delete(long id);

}
