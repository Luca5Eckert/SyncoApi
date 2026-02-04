package com.api.synco.module.authentication.application.controller;

import com.api.synco.core.security.jwt.JwtTokenProvider;
import com.api.synco.module.authentication.application.dto.login.UserLoginRequest;
import com.api.synco.module.authentication.application.dto.register.UserRegisterRequest;
import com.api.synco.module.authentication.application.dto.reset_password.UserResetRequest;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql(scripts = "/test-data/cleanup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class AuthenticationControllerIntegrationTest {

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

    private UserRegisterRequest registerRequest;
    private UserLoginRequest loginRequest;
    private UserResetRequest resetRequest;

    private String token;

    @BeforeEach
    void setup() {
        String email = "john@example.com";

        registerRequest = new UserRegisterRequest(
            "John Doe",
                email,
            "Strong#Pass123"
        );
        loginRequest = new UserLoginRequest(
                email,
            "Strong#Pass123"
        );
        resetRequest = new UserResetRequest(
                "Strong#Pass123",
                "Strong#Pass1233"
        );

        token = jwtTokenProvider.generateToken(email);
    }

    @DisplayName("POST /api/auth/register - Should register user successfully")
    @Test
    void shouldRegisterUserSuccessfully() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value(registerRequest.name()))
                .andExpect(jsonPath("$.data.email").value(registerRequest.email()))
                .andExpect(jsonPath("$.data.roleUser").value("USER"))
                .andExpect(jsonPath("$.data.id").isNumber());
    }

    @DisplayName("POST /api/auth/register - Should fail when email already exists")
    @Test
    void shouldFailRegisterWhenEmailExists() throws Exception {
        // Arrange - create existing user
        var existingUser = new UserEntity(
            new Name("Existing User"),
            new Email("john@example.com"),
            passwordEncoder.encode("Password#123"),
            RoleUser.USER
        );
        entityManager.persist(existingUser);
        entityManager.flush();

        // Act & Assert
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("POST /api/auth/register - Should fail with weak password")
    @Test
    void shouldFailRegisterWithWeakPassword() throws Exception {
        var weakPasswordRequest = new UserRegisterRequest(
            "John Doe",
            "john@example.com",
            "weak"
        );

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(weakPasswordRequest)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("POST /api/auth/register - Should fail with invalid email")
    @Test
    void shouldFailRegisterWithInvalidEmail() throws Exception {
        var invalidEmailRequest = new UserRegisterRequest(
            "John Doe",
            "invalid-email",
            "Strong#Pass123"
        );

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidEmailRequest)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("POST /api/auth/login - Should login successfully with valid credentials")
    @Test
    void shouldLoginSuccessfully() throws Exception {
        // Arrange - create user first
        var user = new UserEntity(
            new Name("John Doe"),
            new Email("john@example.com"),
            passwordEncoder.encode("Strong#Pass123"),
            RoleUser.USER
        );
        entityManager.persist(user);
        entityManager.flush();

        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.data.email").value("john@example.com"))
                .andExpect(jsonPath("$.data.token").isString())
                .andExpect(jsonPath("$.data.roles").isArray());
    }

    @DisplayName("POST /api/auth/login - Should fail with incorrect password")
    @Test
    void shouldFailLoginWithIncorrectPassword() throws Exception {
        // Arrange - create user first
        var user = new UserEntity(
            new Name("John Doe"),
            new Email("john@example.com"),
            passwordEncoder.encode("Strong#Pass123"),
            RoleUser.USER
        );
        entityManager.persist(user);
        entityManager.flush();

        var wrongPasswordRequest = new UserLoginRequest(
            "john@example.com",
            "WrongPassword123"
        );

        // Act & Assert - Spring Security returns 400 Bad Request for bad credentials
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(wrongPasswordRequest)))
                .andExpect(status().isInternalServerError());
    }

    @DisplayName("POST /api/auth/login - Should fail with non-existent user")
    @Test
    void shouldFailLoginWithNonExistentUser() throws Exception {
        // Spring Security returns 400 Bad Request for bad credentials
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("PATCH /api/auth/password - Should fail with non-existent user")
    @Test
    void shouldResetPasswordWithSuccess() throws Exception {
        var user = new UserEntity(
                new Name("John Doe"),
                new Email("john@example.com"),
                passwordEncoder.encode("Strong#Pass123"),
                RoleUser.USER
        );
        entityManager.persist(user);

        mockMvc.perform(patch("/api/auth/password")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resetRequest)))
                .andExpect(status().isAccepted());
    }


}
