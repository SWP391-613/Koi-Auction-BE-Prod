package com.swp391.koibe.repositories;

import com.swp391.koibe.models.User;
import com.swp391.koibe.responses.UserResponse;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Page<User> findByRoleName(String roleName, Pageable pageable);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
}
