package com.swp391.koibe.controllers;

import com.swp391.koibe.dtos.StaffRegisterDTO;
import com.swp391.koibe.dtos.UserDTO;
import com.swp391.koibe.dtos.UserRegisterDTO;
import com.swp391.koibe.exceptions.InvalidApiPathVariableException;
import com.swp391.koibe.exceptions.MethodArgumentNotValidException;
import com.swp391.koibe.exceptions.PermissionDeniedException;
import com.swp391.koibe.models.User;
import com.swp391.koibe.responses.UserResponse;
import com.swp391.koibe.responses.pagination.UserPaginationResponse;
import com.swp391.koibe.services.user.staff.IStaffService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/staffs")
public class StaffController {

    private final IStaffService staffService;

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<UserPaginationResponse> getAllStaffs(
        @RequestParam("page") int page,
        @RequestParam("limit") int limit
    ) {
        try {
            PageRequest pageRequest = PageRequest.of(page, limit);
            Page<UserResponse> staffs = staffService.getAllStaffs(pageRequest);

            UserPaginationResponse response = new UserPaginationResponse();
            response.setItem(staffs.getContent());
            response.setTotalPage(staffs.getTotalPages());
            response.setTotalItem(staffs.getTotalElements());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER')")
    public ResponseEntity<?> getStaffById(@PathVariable long id) {
        UserResponse staff = staffService.findById(id);
        return ResponseEntity.ok(staff);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> createStaff(
        @Valid @RequestBody StaffRegisterDTO staff,
        BindingResult result
    ) throws PermissionDeniedException {
        if(result.hasErrors())
            throw new MethodArgumentNotValidException(result);
        return ResponseEntity.ok(staffService.createNewStaff(staff));
    }

    //update an existing member to staff
    @PutMapping("/new/{id}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_STAFF')")
    public ResponseEntity<?> createNewStaff(
        @PathVariable long id
    ){
        if(id <= 0)
            throw new InvalidApiPathVariableException("Staff id must be greater than 0");
        User newStaff = staffService.updateMemberToStaff(id);
        return ResponseEntity.ok(newStaff);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_STAFF')")
    public ResponseEntity<?> updateStaff(
        @PathVariable long id,
        @Valid @RequestBody UserDTO staff,
        BindingResult result) {

        if(result.hasErrors())
            throw new MethodArgumentNotValidException(result);

        return ResponseEntity.ok(staffService.update(id, staff));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> deleteStaff(@PathVariable long id) {
        staffService.delete(id);
        return ResponseEntity.ok("Delete staff successfully");
    }

}
