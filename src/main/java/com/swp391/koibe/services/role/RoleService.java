package com.swp391.koibe.services.role;

import com.swp391.koibe.dtos.RoleDTO;
import com.swp391.koibe.exceptions.base.DataAlreadyExistException;
import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.Role;
import com.swp391.koibe.repositories.RoleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService{
    private final RoleRepository roleRepository;
    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role createRole(RoleDTO roleDTO) {
        if(roleRepository.findByName(roleDTO.name()).isPresent()){
            throw new DataAlreadyExistException("Role with name " + roleDTO.name() + " already exist");
        }

        Role newRole = Role.builder()
            .name(roleDTO.name())
            .build();

        return roleRepository.save(newRole);
    }

    @Override
    public Role updateRole(long id, RoleDTO roleDTO) {
        Role existingRole = roleRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException("Role with id " + id + " not found"));
        existingRole.setName(roleDTO.name());
        return roleRepository.save(existingRole);
    }

    @Override
    public void deleteRole(Long id) {
        Role existingRole = roleRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException("Role with id " + id + " not found"));
        roleRepository.delete(existingRole);
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException("Role with id " + id + " not found"));
    }
}
