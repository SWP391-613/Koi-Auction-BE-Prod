package com.swp391.koibe.controllers;

import com.swp391.koibe.repositories.OrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/orders_details")
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderDetailRepository orderDetailRepository;

}
