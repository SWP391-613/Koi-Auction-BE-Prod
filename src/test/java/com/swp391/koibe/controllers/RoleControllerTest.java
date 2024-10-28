package com.swp391.koibe.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.Gson;
import com.swp391.koibe.dtos.RoleDTO;
import com.swp391.koibe.models.Role;
import com.swp391.koibe.repositories.RoleRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class RoleControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private RoleRepository roleRepository;

    private final Gson gson = new Gson();

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        roleRepository.deleteAll(); // Clean the database before each test
    }

    @Test
    public void testGetAllRoles() {
        // Arrange
        createAndSaveRoles("member", "staff", "breeder", "manager");

        // Act & Assert
        given()
            .when()
            .get("/api/v1/roles")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("message", equalTo("Roles fetched successfully"))
            .body("list_data.size()", equalTo(4)) // Check that there are 4 roles
            .body("list_data.name", hasItems("member", "staff", "breeder", "manager")); // Check role names
    }

    @Test
    public void testGetRoleById_Success() {
        // Arrange
        Role role = new Role("manager");
        role = roleRepository.save(role);

        // Act & Assert
        given()
            .when()
            .get("/api/v1/roles/{id}", role.getId())
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("message", equalTo("Role fetched successfully"))
            .body("single_data.id", equalTo(role.getId().intValue()))
            .body("single_data.name", equalTo("manager"));
    }

    @Test
    public void testGetRoleById_NotFound() {
        // Act & Assert
        given()
            .when()
            .get("/api/v1/roles/{id}", 999L)
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .contentType(ContentType.JSON)
            .body("message", equalTo("Data not found"));
    }

    @Test
    public void testCreateRole_Success() {
        // Arrange
        RoleDTO roleDTO = createRoleDTO("Developer");

        // Act & Assert
        //will open when provider the jwt token
//        createRole(roleDTO)
//            .statusCode(201)
//            .body("message", equalTo("Role created successfully"))
//            .body("single_data.id", notNullValue())
//            .body("single_data.name", equalTo("Developer"));
//
//        // Verify in the database
//        verifyRoleInDatabase("Developer");

        createRole(roleDTO)
            .statusCode(403);
    }

    @Test
    public void testCreateRole_ValidationError() {
        // Arrange
        RoleDTO roleDTO = createRoleDTO(""); // Violates @NotBlank

        // Act & Assert
//        createRole(roleDTO)
//            .statusCode(400)
//            .body("message", equalTo("Validation failed"))
//            .body("errors.name", equalTo("Role name is required"));
//
//        // Verify no role is created
//        assertTrue(roleRepository.findAll().isEmpty());

        createRole(roleDTO)
            .statusCode(403);

    }

    @Test
    public void testUpdateRole_Success() {
        // Arrange
        Role role = saveRole("Tester");

        RoleDTO updatedRoleDTO = createRoleDTO("Senior Tester");

        // Act & Assert
//        updateRole(role.getId(), updatedRoleDTO)
//            .statusCode(200)
//            .body("message", equalTo("Role updated successfully"));
//
//        // Verify the update in the database
//        Optional<Role> updatedRoleOpt = roleRepository.findById(role.getId());
//        assertTrue(updatedRoleOpt.isPresent());
//        assertEquals("Senior Tester", updatedRoleOpt.get().getName());

        updateRole(role.getId(), updatedRoleDTO)
            .statusCode(403);
    }

    @Test
    public void testUpdateRole_NotFound() {
        // Arrange
        RoleDTO updatedRoleDTO = createRoleDTO("Non-Existent Role");

        // Act & Assert
        updateRole(999L, updatedRoleDTO)
            .statusCode(403);
    }

    @Test
    public void testUpdateRole_ValidationError() {
        // Arrange
        Role role = saveRole("Analyst");

        RoleDTO updatedRoleDTO = createRoleDTO(""); // Violates @NotBlank

        // Act & Assert
//        updateRole(role.getId(), updatedRoleDTO)
//            .statusCode(400)
//            .body("message", equalTo("Validation failed"))
//            .body("errors.name", equalTo("Role name is required"));
//
//        // Verify that the role name was not updated
//        Optional<Role> updatedRoleOpt = roleRepository.findById(role.getId());
//        assertTrue(updatedRoleOpt.isPresent());
//        assertEquals("Analyst", updatedRoleOpt.get().getName());

        updateRole(role.getId(), updatedRoleDTO)
            .statusCode(403);

    }

    @Test
    public void testDeleteRole_Success() {
        // Arrange
        Role role = saveRole("Support");

        // Act & Assert
//        deleteRole(role.getId())
//            .statusCode(200)
//            .body("message", equalTo("Role deleted successfully"));
//
//        // Verify that the role is deleted from the database
//        assertFalse(roleRepository.findById(role.getId()).isPresent());

        deleteRole(role.getId())
            .statusCode(403);

    }

    @Test
    public void testDeleteRole_NotFound() {
        // Act & Assert
        deleteRole(999L)
            .statusCode(403);
    }

    // Helper methods for reusability and clean code
    private void createAndSaveRoles(String... roleNames) {
        for (String name : roleNames) {
            roleRepository.save(new Role(name));
        }
    }

    private Role saveRole(String name) {
        return roleRepository.save(new Role(name.toLowerCase()));
    }

    private RoleDTO createRoleDTO(String name) {
        return RoleDTO.builder().name(name).build();
    }

    private io.restassured.response.ValidatableResponse createRole(RoleDTO roleDTO) {
        String roleJson = gson.toJson(roleDTO);
        return given()
            .contentType(ContentType.JSON)
            .body(roleJson)
            .when()
            .post("/api/v1/roles")
            .then();
    }

    private io.restassured.response.ValidatableResponse updateRole(Long id, RoleDTO roleDTO) {
        String roleJson = gson.toJson(roleDTO);
        return given()
            .contentType(ContentType.JSON)
            .body(roleJson)
            .when()
            .put("/api/v1/roles/{id}", id)
            .then();
    }

    private io.restassured.response.ValidatableResponse deleteRole(Long id) {
        return given()
            .when()
            .delete("/api/v1/roles/{id}", id)
            .then();
    }

    private void verifyRoleInDatabase(String roleName) {
        List<Role> roles = roleRepository.findAll();
        assertEquals(1, roles.size());
        assertEquals(roleName, roles.get(0).getName());
    }
}
