package com.swp391.koibe.controllers;

import com.swp391.koibe.dtos.RoleDTO;
import com.swp391.koibe.exceptions.MethodArgumentNotValidException;
import com.swp391.koibe.models.Role;
import com.swp391.koibe.responses.RoleResponse;
import com.swp391.koibe.services.role.IRoleService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/roles")
@RequiredArgsConstructor
@Slf4j
public class RoleController {

    private final IRoleService roleService;

    @GetMapping("")
    public ResponseEntity<RoleResponse> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();

        RoleResponse response = new RoleResponse();
        response.setMessage("Roles fetched successfully");
        response.setListData(roles);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable Long id) {
        Role role = roleService.getRoleById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
            RoleResponse.builder()
                .message("Role fetched successfully")
                .singleData(role)
                .build());
    }

    @PostMapping("")
    public ResponseEntity<RoleResponse> createRole(
        @Valid @RequestBody RoleDTO roleDTO,
        BindingResult bindingResult) {

        // Check if there are validation errors
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(bindingResult);
        }

        try {
            Role newRole = roleService.createRole(roleDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                RoleResponse.builder()
                    .message("Role created successfully")
                    .singleData(newRole)
                    .build());
        } catch (Exception e) {
            log.error("Error while creating role", e);
            throw new RuntimeException("Error while creating role",
                                       e); // Rethrow as a generic exception
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> updateRole(
        @PathVariable Long id,
        @Valid @RequestBody RoleDTO roleDTO) {
        roleService.updateRole(id, roleDTO);
        return ResponseEntity.ok(
            RoleResponse.builder()
                .message("Role updated successfully")
                .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RoleResponse> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok(
            RoleResponse.builder()
                .message("Role deleted successfully")
                .build()
        );
    }

}
