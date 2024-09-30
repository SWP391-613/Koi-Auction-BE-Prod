package com.swp391.koibe.controllers;

import com.swp391.koibe.models.User;
import com.swp391.koibe.responses.UserResponse;
import com.swp391.koibe.services.user.staff.IStaffService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/staffs")
public class StaffController {

    private final IStaffService staffService;

    @GetMapping("")
    public ResponseEntity<List<UserResponse>> getAllStaffs(
        @RequestParam("page") int page,
        @RequestParam("limit") int limit
    ) {
        try {
            PageRequest pageRequest = PageRequest.of(page, limit);
            Page<UserResponse> staffs = staffService.getAllStaffs(pageRequest);
            return ResponseEntity.ok(staffs.getContent());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBreeder(@PathVariable long id) {
        try {
            User staff = staffService.findById(id);
            return ResponseEntity.ok(staff);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
