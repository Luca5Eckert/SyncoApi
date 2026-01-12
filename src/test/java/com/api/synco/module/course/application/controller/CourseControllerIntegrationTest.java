package com.api.synco.module.course.application.controller;

import com.api.synco.shared.security.jwt.JwtTokenProvider;
import com.api.synco.module.course.application.dto.create.CreateCourseRequest;
import com.api.synco.module.course.application.dto.update.UpdateCourseRequest;
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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql(scripts = "/test-data/cleanup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CourseControllerIntegrationTest {

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

        // Create a course for testing
        course = new CourseEntity("Computer Science", "CS", "CS Description");
        entityManager.persist(course);

        entityManager.flush();

        // Generate JWT tokens
        adminToken = jwtTokenProvider.generateToken(adminUser.getEmail().address());
        userToken = jwtTokenProvider.generateToken(regularUser.getEmail().address());
    }

    @DisplayName("POST /api/courses - Should create course successfully as admin")
    @Test
    void shouldCreateCourseSuccessfully() throws Exception {
        var createRequest = new CreateCourseRequest(
            "Mathematics",
            "MATH",
            "Math Desc"
        );

        mockMvc.perform(post("/api/courses")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value("Mathematics"))
                .andExpect(jsonPath("$.data.acronym").value("MATH"));
    }

    @DisplayName("POST /api/courses - Should fail when user is not admin")
    @Test
    void shouldFailCreateCourseWhenNotAdmin() throws Exception {
        var createRequest = new CreateCourseRequest(
            "Physics",
            "PHY",
            "PhyDesc"
        );

        mockMvc.perform(post("/api/courses")
                .header("Authorization", "Bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("POST /api/courses - Should fail when course already exists")
    @Test
    void shouldFailCreateCourseWhenDuplicate() throws Exception {
        var createRequest = new CreateCourseRequest(
            "Computer Science",
            "CS",
            "Duplicate"
        );

        mockMvc.perform(post("/api/courses")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("GET /api/courses/{id} - Should get course by ID")
    @Test
    void shouldGetCourseById() throws Exception {
        mockMvc.perform(get("/api/courses/" + course.getId())
                .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Computer Science"))
                .andExpect(jsonPath("$.data.acronym").value("CS"));
    }

    @DisplayName("GET /api/courses/{id} - Should fail when course not found")
    @Test
    void shouldFailGetCourseWhenNotFound() throws Exception {
        mockMvc.perform(get("/api/courses/99999")
                .header("Authorization", "Bearer " + userToken))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("GET /api/courses - Should get all courses")
    @Test
    void shouldGetAllCourses() throws Exception {
        // Add more courses
        entityManager.persist(new CourseEntity("Mathematics", "MATH", "Math Desc"));
        entityManager.persist(new CourseEntity("Physics", "PHY", "Physics Desc"));
        entityManager.flush();

        mockMvc.perform(get("/api/courses")
                .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(3)));
    }

    @DisplayName("GET /api/courses - Should filter courses by name")
    @Test
    void shouldFilterCoursesByName() throws Exception {
        entityManager.persist(new CourseEntity("Mathematics", "MATH", "Math Desc"));
        entityManager.flush();

        mockMvc.perform(get("/api/courses")
                .header("Authorization", "Bearer " + userToken)
                .param("name", "Computer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].name").value("Computer Science"));
    }


    @DisplayName("PATCH /api/courses/{id} - Should update course as admin")
    @Test
    void shouldUpdateCourseAsAdmin() throws Exception {
        // 1. Crie o objeto de request
        UpdateCourseRequest request = new UpdateCourseRequest(
                "Updated CS",
                "UCS",
                "Updated"
        );

        mockMvc.perform(patch("/api/courses/" + course.getId())
                        .header("Authorization", "Bearer " + adminToken)
                        // 2. Mude para APPLICATION_JSON
                        .contentType(MediaType.APPLICATION_JSON)
                        // 3. Converta o objeto para JSON String e envie no body (.content)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.data.name").value("Updated CS"))
                .andExpect(jsonPath("$.data.acronym").value("UCS"));
    }

    @DisplayName("PATCH /api/courses/{id} - Should fail when user is not admin")
    @Test
    void shouldFailUpdateCourseWhenNotAdmin() throws Exception {
        mockMvc.perform(patch("/api/courses/" + course.getId())
                .header("Authorization", "Bearer " + userToken)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "Hacked CS")
                .param("acronym", "HCS")
                .param("description", "Hacked"))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("DELETE /api/courses/{id} - Should delete course as admin")
    @Test
    void shouldDeleteCourseAsAdmin() throws Exception {
        mockMvc.perform(delete("/api/courses/" + course.getId())
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isAccepted());
    }

    @DisplayName("DELETE /api/courses/{id} - Should fail when user is not admin")
    @Test
    void shouldFailDeleteCourseWhenNotAdmin() throws Exception {
        mockMvc.perform(delete("/api/courses/" + course.getId())
                .header("Authorization", "Bearer " + userToken))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("DELETE /api/courses/{id} - Should fail when course not found")
    @Test
    void shouldFailDeleteCourseWhenNotFound() throws Exception {
        mockMvc.perform(delete("/api/courses/99999")
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().is4xxClientError());
    }
}
