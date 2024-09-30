package com.swp391.koibe.services.role;

import com.swp391.koibe.dtos.RoleDTO;
import com.swp391.koibe.models.Role;
import java.util.List;

public interface IRoleService {
    List<Role> getAllRoles();
    Role createRole(RoleDTO roleDTO);
    Role updateRole(long id, RoleDTO roleDTO);
    void deleteRole(Long id);
    Role getRoleById(Long id);
}
