package com.api.synco.module.course.domain.use_cases;

import com.api.synco.module.course.application.dto.update.UpdateCourseRequest;
import com.api.synco.module.course.domain.CourseEntity;
import com.api.synco.module.course.domain.exception.CourseNotFoundException;
import com.api.synco.module.course.domain.port.CourseRepository;
import com.api.synco.module.permission.domain.service.PermissionService;
import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.enumerator.RoleUser;
import com.api.synco.module.user.domain.exception.UserNotFoundDomainException;
import com.api.synco.module.user.domain.exception.permission.UserWithoutEditUserPermissionException;
import com.api.synco.module.user.domain.port.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateCourseUseCaseTest {

    @Mock
    private PermissionService permissionService;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UpdateCourseUseCase updateCourseUseCase;

    private long courseId;
    private long userId;
    private CourseEntity existingCourse;
    private UserEntity adminUser;
    private UpdateCourseRequest updateRequest;

    @BeforeEach
    void setup() {
        courseId = 1L;
        userId = 2L;
        existingCourse = new CourseEntity(courseId, "Old Name", "ON", "Old Description");
        adminUser = new UserEntity(userId, null, null, null, RoleUser.ADMIN);
        updateRequest = new UpdateCourseRequest("Updated Name", "UN", "Updated Description");
    }

    @DisplayName("Should update course successfully when user has permission")
    @Test
    void shouldUpdateCourseSuccessfully() {
        // arrange
        when(userRepository.findById(userId)).thenReturn(Optional.of(adminUser));
        when(permissionService.canModifyCourse(RoleUser.ADMIN)).thenReturn(true);
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(existingCourse));

        // act
        var result = updateCourseUseCase.execute(updateRequest, courseId, userId);

        // assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Updated Name");
        assertThat(result.getAcronym()).isEqualTo("UN");
        assertThat(result.getDescription()).isEqualTo("Updated Description");

        var captor = ArgumentCaptor.forClass(CourseEntity.class);
        verify(courseRepository).save(captor.capture());
        var saved = captor.getValue();
        assertThat(saved.getName()).isEqualTo("Updated Name");
        assertThat(saved.getAcronym()).isEqualTo("UN");
    }

    @DisplayName("Should throw UserNotFoundDomainException when user not found")
    @Test
    void shouldThrowUserNotFoundExceptionWhenUserNotFound() {
        // arrange
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // act and assert
        assertThatThrownBy(() -> updateCourseUseCase.execute(updateRequest, courseId, userId))
                .isInstanceOf(UserNotFoundDomainException.class);

        verify(courseRepository, never()).findById(anyLong());
        verify(courseRepository, never()).save(any());
    }

    @DisplayName("Should throw UserWithoutEditUserPermissionException when user lacks permission")
    @Test
    void shouldThrowPermissionExceptionWhenUserLacksPermission() {
        // arrange
        var regularUser = new UserEntity(userId, null, null, null, RoleUser.USER);
        when(userRepository.findById(userId)).thenReturn(Optional.of(regularUser));
        when(permissionService.canModifyCourse(RoleUser.USER)).thenReturn(false);

        // act and assert
        assertThatThrownBy(() -> updateCourseUseCase.execute(updateRequest, courseId, userId))
                .isInstanceOf(UserWithoutEditUserPermissionException.class);

        verify(courseRepository, never()).findById(anyLong());
        verify(courseRepository, never()).save(any());
    }

    @DisplayName("Should throw CourseNotFoundException when course not found")
    @Test
    void shouldThrowCourseNotFoundExceptionWhenCourseNotFound() {
        // arrange
        when(userRepository.findById(userId)).thenReturn(Optional.of(adminUser));
        when(permissionService.canModifyCourse(RoleUser.ADMIN)).thenReturn(true);
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // act and assert
        assertThatThrownBy(() -> updateCourseUseCase.execute(updateRequest, courseId, userId))
                .isInstanceOf(CourseNotFoundException.class);

        verify(courseRepository, never()).save(any());
    }
}
