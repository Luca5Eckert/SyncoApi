package com.api.synco.module.class_entity.application.controller;

import com.api.synco.infrastructure.security.jwt.JwtTokenProvider;
import com.api.synco.module.class_entity.application.dto.create.CreateClassRequest;
import com.api.synco.module.class_entity.application.dto.update.UpdateClassRequest;
import com.api.synco.module.class_entity.domain.ClassEntity;
import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.class_entity.domain.enumerator.Shift;
import com.api.synco.module.course.domain.CourseEntity;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql(scripts = "/test-data/cleanup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ClassControllerIntegrationTest {

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
    private CourseEntity course;
    private ClassEntity classEntity;

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

        // Create a course
        course = new CourseEntity("Computer Science", "CS", "CS Description");
        entityManager.persist(course);

        // Create a class
        classEntity = new ClassEntity(
            new ClassEntityId(course.getId(), 1),
            course,
            800,
            Shift.FIRST_SHIFT
        );
        entityManager.persist(classEntity);

        entityManager.flush();

        // Generate JWT tokens
        adminToken = jwtTokenProvider.generateToken(adminUser.getEmail().address());
        userToken = jwtTokenProvider.generateToken(regularUser.getEmail().address());
    }

    @DisplayName("POST /api/classes - Should create class successfully as admin")
    @Test
    void shouldCreateClassSuccessfully() throws Exception {
        var createRequest = new CreateClassRequest(
            course.getId(),
            600,
            Shift.SECOND_SHIFT
        );

        mockMvc.perform(post("/api/classes")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.totalHours").value(600))
                .andExpect(jsonPath("$.data.shift").value("SECOND_SHIFT"));
    }

    @DisplayName("POST /api/classes - Should fail when user lacks permission")
    @Test
    void shouldFailCreateClassWhenUserLacksPermission() throws Exception {
        // TODO: Application throws unhandled exception instead of returning 403 Forbidden.
        // Expected behavior: should return 403 Forbidden.
        // Current behavior: returns 500 Internal Server Error.
        var createRequest = new CreateClassRequest(
            course.getId(),
            700,
            Shift.FIRST_SHIFT
        );

        mockMvc.perform(post("/api/classes")
                .header("Authorization", "Bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isForbidden());
    }

    @DisplayName("POST /api/classes - Should fail when course not found")
    @Test
    void shouldFailCreateClassWhenCourseNotFound() throws Exception {
        var createRequest = new CreateClassRequest(
            99999L,
            600,
            Shift.FIRST_SHIFT
        );

        mockMvc.perform(post("/api/classes")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("GET /api/classes/{idCourse}/{numberClass} - Should get class by ID")
    @Test
    void shouldGetClassById() throws Exception {
        mockMvc.perform(get("/api/classes/" + course.getId() + "/1")
                .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.totalHours").value(800))
                .andExpect(jsonPath("$.data.shift").value("FIRST_SHIFT"));
    }

    @DisplayName("GET /api/classes/{idCourse}/{numberClass} - Should fail when class not found")
    @Test
    void shouldFailGetClassWhenNotFound() throws Exception {
        // TODO: Application throws unhandled exception instead of returning 404 Not Found.
        // Expected behavior: should return 404 Not Found.
        // Current behavior: returns 500 Internal Server Error.
        mockMvc.perform(get("/api/classes/" + course.getId() + "/999")
                .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isNotFound());
    }

    @DisplayName("PUT /api/classes/{idCourse}/{numberClass} - Should update class as admin")
    @Test
    void shouldUpdateClassAsAdmin() throws Exception {
        // TODO: Application throws unhandled exception during update.
        // Expected behavior: should return 202 Accepted with updated class data.
        // Current behavior: returns 500 Internal Server Error.
        var updateRequest = new UpdateClassRequest(
            1000,
            Shift.SECOND_SHIFT
        );

        mockMvc.perform(put("/api/classes/" + course.getId() + "/1")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.data.totalHours").value(1000))
                .andExpect(jsonPath("$.data.shift").value("SECOND_SHIFT"));
    }

    @DisplayName("PUT /api/classes/{idCourse}/{numberClass} - Should fail when user lacks permission")
    @Test
    void shouldFailUpdateClassWhenUserLacksPermission() throws Exception {
        // TODO: Application throws unhandled exception instead of returning 403 Forbidden.
        // Expected behavior: should return 403 Forbidden.
        // Current behavior: returns 500 Internal Server Error.
        var updateRequest = new UpdateClassRequest(
            500,
            Shift.FIRST_SHIFT
        );

        mockMvc.perform(put("/api/classes/" + course.getId() + "/1")
                .header("Authorization", "Bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isForbidden());
    }

    @DisplayName("DELETE /api/classes/{idCourse}/{numberClass} - Should delete class as admin")
    @Test
    void shouldDeleteClassAsAdmin() throws Exception {
        mockMvc.perform(delete("/api/classes/" + course.getId() + "/1")
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk());
    }

    @DisplayName("DELETE /api/classes/{idCourse}/{numberClass} - Should fail when user lacks permission")
    @Test
    void shouldFailDeleteClassWhenUserLacksPermission() throws Exception {
        // TODO: Application throws unhandled exception instead of returning 403 Forbidden.
        // Expected behavior: should return 403 Forbidden.
        // Current behavior: returns 500 Internal Server Error.
        mockMvc.perform(delete("/api/classes/" + course.getId() + "/1")
                .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isForbidden());
    }

    @DisplayName("DELETE /api/classes/{idCourse}/{numberClass} - Should fail when class not found")
    @Test
    void shouldFailDeleteClassWhenNotFound() throws Exception {
        // TODO: Application throws unhandled exception instead of returning 404 Not Found.
        // Expected behavior: should return 404 Not Found.
        // Current behavior: returns 500 Internal Server Error.
        mockMvc.perform(delete("/api/classes/" + course.getId() + "/999")
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isNotFound());
    }
}
