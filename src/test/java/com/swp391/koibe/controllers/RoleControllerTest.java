//package com.swp391.koibe.controllers;
//
//import com.google.gson.Gson;
//import com.swp391.koibe.dtos.RoleDTO;
//import com.swp391.koibe.models.Role;
//import com.swp391.koibe.repositories.RoleRepository;
//import io.restassured.RestAssured;
//import io.restassured.http.ContentType;
//import java.util.List;
//import java.util.Optional;
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
//
//import static io.restassured.RestAssured.*;
//import static org.hamcrest.Matchers.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("dev")
//public class RoleControllerTest {
//
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    private Gson gson = new Gson();
//
//    @BeforeEach
//    public void setUp() {
//        RestAssured.port = port;
//        roleRepository.deleteAll(); // Clean the database before each test
//    }
//
//    @Test
//    public void testGetAllRoles() {
//        // Arrange
//        Role role1 = new Role("Admin");
//        Role role2 = new Role("User");
//        roleRepository.save(role1);
//        roleRepository.save(role2);
//
//        // Act & Assert
//        given()
//            .when()
//            .get("/api/v1/roles")
//            .then()
//            .statusCode(200)
//            .contentType(ContentType.JSON)
//            .body("message", equalTo("Roles fetched successfully"))
//            .body("listData.size()", equalTo(2))
//            .body("listData.name", hasItems("Admin", "User"));
//    }
//
//    @Test
//    public void testGetRoleById_Success() {
//        // Arrange
//        Role role = new Role("Manager");
//        role = roleRepository.save(role);
//
//        // Act & Assert
//        given()
//            .when()
//            .get("/api/roles/{id}", role.getId())
//            .then()
//            .statusCode(200)
//            .contentType(ContentType.JSON)
//            .body("message", equalTo("Role fetched successfully"))
//            .body("singleData.id", equalTo(role.getId().intValue()))
//            .body("singleData.name", equalTo("Manager"));
//    }
//
//    @Test
//    public void testGetRoleById_NotFound() {
//        // Act & Assert
//        given()
//            .when()
//            .get("/api/roles/{id}", 999L)
//            .then()
//            .statusCode(404)
//            .contentType(ContentType.JSON)
//            .body("message", equalTo("Role not found"));
//    }
//
//    @Test
//    public void testCreateRole_Success() {
//        // Arrange
//        RoleDTO roleDTO = RoleDTO.builder()
//            .name("Developer")
//            .build();
//
//        String roleJson = gson.toJson(roleDTO);
//
//        // Act & Assert
//        given()
//            .contentType(ContentType.JSON)
//            .body(roleJson)
//            .when()
//            .post("/api/roles")
//            .then()
//            .statusCode(201)
//            .contentType(ContentType.JSON)
//            .body("message", equalTo("Role created successfully"))
//            .body("singleData.id", notNullValue())
//            .body("singleData.name", equalTo("Developer"));
//
//        // Verify in the database
//        List<Role> roles = roleRepository.findAll();
//        assertEquals(1, roles.size());
//        assertEquals("Developer", roles.get(0).getName());
//    }
//
//    @Test
//    public void testCreateRole_ValidationError() {
//        // Arrange
//        RoleDTO roleDTO = RoleDTO.builder()
//            .name("") // Violates @NotBlank
//            .build();
//
//        String roleJson = gson.toJson(roleDTO);
//
//        // Act & Assert
//        given()
//            .contentType(ContentType.JSON)
//            .body(roleJson)
//            .when()
//            .post("/api/roles")
//            .then()
//            .statusCode(400)
//            .contentType(ContentType.JSON)
//            .body("message", equalTo("Validation failed"))
//            .body("errors.name", equalTo("Role name is required"));
//
//        // Verify no role is created
//        List<Role> roles = roleRepository.findAll();
//        assertTrue(roles.isEmpty());
//    }
//
//    @Test
//    public void testUpdateRole_Success() {
//        // Arrange
//        Role role = new Role("Tester");
//        role = roleRepository.save(role);
//
//        // Create an updated RoleDTO using the builder
//        RoleDTO updatedRoleDTO = RoleDTO.builder()
//            .name("Senior Tester")
//            .build();
//
//        String updatedRoleJson = gson.toJson(updatedRoleDTO);
//
//        // Act & Assert
//        given()
//            .contentType(ContentType.JSON)
//            .body(updatedRoleJson)
//            .when()
//            .put("/api/roles/{id}", role.getId())
//            .then()
//            .statusCode(200)
//            .contentType(ContentType.JSON)
//            .body("message", equalTo("Role updated successfully"));
//
//        // Verify the update in the database
//        Optional<Role> updatedRoleOpt = roleRepository.findById(role.getId());
//        assertTrue(updatedRoleOpt.isPresent());
//        assertEquals("Senior Tester", updatedRoleOpt.get().getName());
//    }
//
//    @Test
//    public void testUpdateRole_NotFound() {
//        // Arrange
//        RoleDTO updatedRoleDTO = RoleDTO.builder()
//            .name("Non-Existent Role")
//            .build();
//
//        String updatedRoleJson = gson.toJson(updatedRoleDTO);
//
//        // Act & Assert
//        given()
//            .contentType(ContentType.JSON)
//            .body(updatedRoleJson)
//            .when()
//            .put("/api/roles/{id}", 999L)
//            .then()
//            .statusCode(404)
//            .contentType(ContentType.JSON)
//            .body("message", equalTo("Role not found"));
//    }
//
//    @Test
//    public void testUpdateRole_ValidationError() {
//        // Arrange
//        Role role = new Role("Analyst");
//        role = roleRepository.save(role);
//
//        // Create an invalid RoleDTO using the builder
//        RoleDTO updatedRoleDTO = RoleDTO.builder()
//            .name("") // Violates @NotBlank
//            .build();
//
//        String updatedRoleJson = gson.toJson(updatedRoleDTO);
//
//        // Act & Assert
//        given()
//            .contentType(ContentType.JSON)
//            .body(updatedRoleJson)
//            .when()
//            .put("/api/roles/{id}", role.getId())
//            .then()
//            .statusCode(400)
//            .contentType(ContentType.JSON)
//            .body("message", equalTo("Validation failed"))
//            .body("errors.name", equalTo("Role name is required"));
//
//        // Verify that the role name was not updated
//        Optional<Role> updatedRoleOpt = roleRepository.findById(role.getId());
//        assertTrue(updatedRoleOpt.isPresent());
//        assertEquals("Analyst", updatedRoleOpt.get().getName());
//    }
//
//    @Test
//    public void testDeleteRole_Success() {
//        // Arrange
//        Role role = new Role("Support");
//        role = roleRepository.save(role);
//
//        // Act & Assert
//        given()
//            .when()
//            .delete("/api/roles/{id}", role.getId())
//            .then()
//            .statusCode(200)
//            .contentType(ContentType.JSON)
//            .body("message", equalTo("Role deleted successfully"));
//
//        // Verify that the role is deleted from the database
//        Optional<Role> deletedRole = roleRepository.findById(role.getId());
//        assertFalse(deletedRole.isPresent());
//    }
//
//    @Test
//    public void testDeleteRole_NotFound() {
//        // Act & Assert
//        given()
//            .when()
//            .delete("/api/roles/{id}", 999L)
//            .then()
//            .statusCode(404)
//            .contentType(ContentType.JSON)
//            .body("message", equalTo("Role not found"));
//    }
//}
