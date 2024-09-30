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
        RoleResponse response = new RoleResponse();
        response.setMessage("Role fetched successfully");
        response.setSingleData(role);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("")
    public ResponseEntity<RoleResponse> createRole(
        @Valid @RequestBody RoleDTO roleDTO,
        BindingResult bindingResult) {

        RoleResponse response = new RoleResponse();

        // Check if there are validation errors
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(bindingResult);
        }

        try {
            Role newRole = roleService.createRole(roleDTO);
            response.setMessage("Role created successfully");
            response.setSingleData(newRole);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("Error while creating role", e);
            response.setMessage("Error while creating role");
            throw new RuntimeException("Error while creating role", e); // Rethrow as a generic exception
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> updateRole(
            @PathVariable Long id,
            @Valid @RequestBody RoleDTO roleDTO) {
        Role updatedRole = roleService.updateRole(id, roleDTO);

        RoleResponse response = new RoleResponse();
        response.setMessage("Role updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RoleResponse> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        RoleResponse response = new RoleResponse();
        response.setMessage("Role deleted successfully");
        return ResponseEntity.ok(response);
    }

}
