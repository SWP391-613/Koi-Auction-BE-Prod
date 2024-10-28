package com.swp391.koibe.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swp391.koibe.components.JwtTokenUtils;
import com.swp391.koibe.dtos.RoleDTO;
import com.swp391.koibe.dtos.UserLoginDTO;
import com.swp391.koibe.models.User;
import com.swp391.koibe.services.token.TokenService;
import com.swp391.koibe.services.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RoleControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // To convert objects to JSON

    @Autowired
    private UserService userService; // Service to call login method

    @Autowired
    private TokenService tokenService; // To handle token generation

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    private String jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjI3LCJlbWFpbCI6ImlzYUBnbWFpbC5jb20iLCJzdWIiOiJpc2FAZ21haWwuY29tIiwiZXhwIjoxNzMyMzgxMzAyfQ.kqRptjGDXns0lFlZZptJHJFLzSrHDvHnGf2LbE6l9rE";

    @BeforeEach
    public void setup() throws Exception {
//        // Simulate a login and retrieve a JWT token
//        UserLoginDTO userLoginDTO = new UserLoginDTO("son@gmail.com", "123456");
//
//        // Assuming the UserService's login method generates a token for the user
//        String token = userService.login(userLoginDTO.getEmail(), userLoginDTO.getPassword());
//
//        // Simulate the request and user agent (optional)
//        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64)";
//        User userDetail = userService.getUserDetailsFromToken(token);
//
//        // Store the JWT token for use in tests
//        jwtToken = jwtTokenUtils.generateToken(userDetail);
    }

    // Utility method to determine if it's a mobile device based on the User-Agent
    private boolean isMobileDevice(String userAgent) {
        return userAgent != null && (userAgent.contains("Mobile") || userAgent.contains("Android"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetAllRoles() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/roles")
                            .header("Authorization", "Bearer " + jwtToken)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Roles fetched successfully"))
            .andExpect(jsonPath("$.list_data").isArray());
    }

//    @Test
//    @WithMockUser(username = "admin", roles = {"ADMIN"})
//    public void testCreateRole() throws Exception {
//        RoleDTO roleDTO = new RoleDTO("user");
//
//        mockMvc.perform(post("/api/v1/roles")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .header("Authorization", "Bearer " + jwtToken)
//                            .content(objectMapper.writeValueAsString(roleDTO)))
//
//            .andExpect(status().isCreated())
//            .andExpect(jsonPath("$.message").value("Role created successfully"))
//            .andExpect(jsonPath("$.single_data.name").value("user"));
//    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testDeleteRole_NotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/roles/{id}", 999L)
                            .header("Authorization", "Bearer " + jwtToken)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("Data not found"))
            .andExpect(jsonPath("$.reason").value("Role with id 999 not found"));
    }

//    @Test
//    @WithMockUser(username = "admin", roles = {"ADMIN"})
//    public void testDeleteRole_Success() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/roles/{id}", 6L)
//                            .header("Authorization", "Bearer " + jwtToken)
//                            .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.message").value("Role deleted successfully"));
//    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testDeleteRole_Unauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/roles/{id}", 999L)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized())
            .andExpect(content().string(""));
    }
}

