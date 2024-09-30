package com.swp391.koibe.services.user.member;

import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.User;
import com.swp391.koibe.responses.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMemberService {

    Page<UserResponse> getAllMembers(Pageable pageable);
    User findById(long id) throws DataNotFoundException;
    User createMember(User member);
    User updateMember(long memberId, User member) throws Exception;
    void deleteMember(long id);

}
