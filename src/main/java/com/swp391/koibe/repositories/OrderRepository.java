package com.swp391.koibe.repositories;

import com.swp391.koibe.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
