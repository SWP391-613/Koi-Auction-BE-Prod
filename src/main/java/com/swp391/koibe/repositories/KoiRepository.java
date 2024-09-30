package com.swp391.koibe.repositories;

import com.swp391.koibe.models.Koi;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KoiRepository extends JpaRepository<Koi, Long> {
    boolean existsByName(String name);
}
