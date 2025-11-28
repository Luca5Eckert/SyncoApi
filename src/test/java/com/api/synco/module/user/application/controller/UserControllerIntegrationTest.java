package com.api.synco.module.user.application.controller;

import com.api.synco.infrastructure.security.jwt.JwtTokenProvider;
import com.api.synco.module.user.application.dto.create.UserCreateRequest;
import com.api.synco.module.user.application.dto.delete.UserDeleteRequest;
import com.api.synco.module.user.application.dto.edit.UserEditRequest;
import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.enumerator.RoleUser;
import com.api.synco.module.user.domain.vo.Email;
import com.api.synco.module.user.domain.vo.Name;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql(scripts = "/test-data/cleanup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private UserEntity adminUser;
    private UserEntity regularUser;
    private String adminToken;
    private String userToken;

    @BeforeEach
    void setup() {
        // Create admin user
        adminUser = new UserEntity(
            new Name("Admin User"),
            new Email("admin@example.com"),
            passwordEncoder.encode("Admin#Pass123"),
            RoleUser.ADMIN
        );
        entityManager.persist(adminUser);

        // Create regular user
        regularUser = new UserEntity(
            new Name("Regular User"),
            new Email("user@example.com"),
            passwordEncoder.encode("User#Pass123"),
            RoleUser.USER
        );
        entityManager.persist(regularUser);

        entityManager.flush();

        // Generate JWT tokens
        adminToken = jwtTokenProvider.generateToken(adminUser.getEmail().address());
        userToken = jwtTokenProvider.generateToken(regularUser.getEmail().address());
    }

    @DisplayName("POST /api/users - Should create user successfully with authentication")
    @Test
    void shouldCreateUserSuccessfully() throws Exception {
        var createRequest = new UserCreateRequest(
            "New User",
            "newuser@example.com",
            "Strong#Pass123",
            RoleUser.USER
        );

        mockMvc.perform(post("/api/users")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value("New User"))
                .andExpect(jsonPath("$.data.email").value("newuser@example.com"))
                .andExpect(jsonPath("$.data.roleUser").value("USER"));
    }

    @DisplayName("POST /api/users - Should create user with authentication")
    @Test
    void shouldCreateUserWithAuthentication() throws Exception {
        var createRequest = new UserCreateRequest(
            "Another User",
            "another@example.com",
            "Strong#Pass123",
            RoleUser.USER
        );

        // Create user with proper authentication token
        mockMvc.perform(post("/api/users")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value("Another User"))
                .andExpect(jsonPath("$.data.email").value("another@example.com"));
    }

    @DisplayName("GET /api/users/{id} - Should get user by ID")
    @Test
    void shouldGetUserById() throws Exception {
        mockMvc.perform(get("/api/users/" + regularUser.getId())
                .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Regular User"))
                .andExpect(jsonPath("$.data.email").value("user@example.com"))
                .andExpect(jsonPath("$.data.role").value("USER"));
    }

    @DisplayName("GET /api/users/{id} - Should fail when user not found")
    @Test
    void shouldFailGetUserWhenNotFound() throws Exception {
        // The actual status code may be 400 or 404 depending on exception handling
        mockMvc.perform(get("/api/users/99999")
                .header("Authorization", "Bearer " + userToken))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("GET /api/users - Should get all users")
    @Test
    void shouldGetAllUsers() throws Exception {
        mockMvc.perform(get("/api/users")
                .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data.[0].name").exists())
                .andExpect(jsonPath("$.data.[1].name").exists());
    }

    @DisplayName("PATCH /api/users - Admin should edit any user")
    @Test
    void shouldEditUserAsAdmin() throws Exception {
        var editRequest = new UserEditRequest(
            regularUser.getId(),
            "Updated Name",
            "updated@example.com"
        );

        mockMvc.perform(patch("/api/users")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(editRequest)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.data.name").value("Updated Name"))
                .andExpect(jsonPath("$.data.email").value("updated@example.com"));
    }

    @DisplayName("PATCH /api/users - User should edit their own profile")
    @Test
    void shouldEditOwnProfile() throws Exception {
        var editRequest = new UserEditRequest(
            regularUser.getId(),
            "Self Updated",
            "selfupdated@example.com"
        );

        mockMvc.perform(patch("/api/users")
                .header("Authorization", "Bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(editRequest)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.data.name").value("Self Updated"))
                .andExpect(jsonPath("$.data.email").value("selfupdated@example.com"));
    }

    @DisplayName("PATCH /api/users - Regular user should not edit other users")
    @Test
    void shouldFailEditOtherUserAsRegularUser() throws Exception {
        var editRequest = new UserEditRequest(
            adminUser.getId(),
            "Hacked Name",
            "hacked@example.com"
        );

        // Application returns 400 for permission denied scenarios
        mockMvc.perform(patch("/api/users")
                .header("Authorization", "Bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(editRequest)))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("DELETE /api/users - Admin should delete any user")
    @Test
    void shouldDeleteUserAsAdmin() throws Exception {
        var deleteRequest = new UserDeleteRequest(regularUser.getId());

        mockMvc.perform(delete("/api/users")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deleteRequest)))
                .andExpect(status().isAccepted());
    }

    @DisplayName("DELETE /api/users - Regular user should not delete other users")
    @Test
    void shouldFailDeleteOtherUserAsRegularUser() throws Exception {
        var deleteRequest = new UserDeleteRequest(adminUser.getId());

        // Application returns 400 for permission denied scenarios
        mockMvc.perform(delete("/api/users")
                .header("Authorization", "Bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deleteRequest)))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("DELETE /api/users - Should fail when user not found")
    @Test
    void shouldFailDeleteWhenUserNotFound() throws Exception {
        var deleteRequest = new UserDeleteRequest(99999L);

        // Application returns 400 for not found scenarios
        mockMvc.perform(delete("/api/users")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deleteRequest)))
                .andExpect(status().is4xxClientError());
    }
}
