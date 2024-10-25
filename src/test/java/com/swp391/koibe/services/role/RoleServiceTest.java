package com.swp391.koibe.services.role;

import com.swp391.koibe.dtos.RoleDTO;
import com.swp391.koibe.models.Role;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class RoleServiceTest {

    @MockBean
    private RoleService roleService;

    @BeforeEach
    public void setUp() {
        Mockito.reset(roleService);
    }

    @Test
    public void testGetAllRoles() {
        List<Role> roles = List.of(
            Role.builder().id(1L).name("Admin").build(),
            Role.builder().id(2L).name("User").build()
        );
        Mockito.when(roleService.getAllRoles()).thenReturn(roles);
    }

    @Test
    @Order(1)
    public void testCreateRole() {
        RoleDTO newRoleDTO = RoleDTO.builder().name("Admin").build();
        Role newRole = Role.builder().name(newRoleDTO.name()).build();
        Mockito.when(roleService.createRole(newRoleDTO)).thenReturn(newRole);
    }

    @Test
    @Order(2)
    public void testUpdateRole() {
        RoleDTO newRoleDTO = RoleDTO.builder().name("Admin").build();
        Role newRole = Role.builder().name(newRoleDTO.name()).build();
        Mockito.when(roleService.updateRole(1L, newRoleDTO)).thenReturn(newRole);
    }

    @Test
    @Order(3)
    public void testDeleteRole() {
        Mockito.doNothing().when(roleService).deleteRole(1L);
    }

    @Test
    @Order(4)
    public void testGetRoleById() {
        Role role = Role.builder().id(1L).name("Admin").build();
        Mockito.when(roleService.getRoleById(1L)).thenReturn(role);
    }

}
