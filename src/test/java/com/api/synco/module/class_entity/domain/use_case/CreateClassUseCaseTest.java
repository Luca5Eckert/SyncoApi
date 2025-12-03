package com.api.synco.module.class_entity.domain.use_case;

import com.api.synco.module.class_entity.application.dto.create.CreateClassRequest;
import com.api.synco.module.class_entity.domain.ClassEntity;
import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.class_entity.domain.enumerator.Shift;
import com.api.synco.module.class_entity.domain.exception.user.UserWithoutCreateClassPermissionException;
import com.api.synco.module.class_entity.domain.port.ClassRepository;
import com.api.synco.module.course.domain.CourseEntity;
import com.api.synco.module.course.domain.exception.CourseNotFoundException;
import com.api.synco.module.course.domain.port.CourseRepository;
import com.api.synco.module.permission.domain.service.PermissionService;
import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.enumerator.RoleUser;
import com.api.synco.module.user.domain.exception.UserNotFoundDomainException;
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
class CreateClassUseCaseTest {

    @Mock
    private PermissionService permissionService;

    @Mock
    private ClassRepository classRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateClassUseCase createClassUseCase;

    private long userId;
    private long courseId;
    private UserEntity adminUser;
    private CourseEntity courseEntity;
    private CreateClassRequest createClassRequest;

    @BeforeEach
    void setup() {
        userId = 1L;
        courseId = 10L;
        adminUser = new UserEntity(userId, null, null, null, RoleUser.ADMIN);
        courseEntity = new CourseEntity(courseId, "Computer Science", "CS", "CS Description");
        createClassRequest = new CreateClassRequest(courseId, 800, Shift.FIRST_SHIFT);
    }

    @DisplayName("Should create class successfully when user has permission")
    @Test
    void shouldCreateClassSuccessfully() {
        // arrange
        when(userRepository.findById(userId)).thenReturn(Optional.of(adminUser));
        when(permissionService.canModifyClass(RoleUser.ADMIN)).thenReturn(true);
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(courseEntity));
        when(classRepository.getNextNumberOfCourse(courseEntity)).thenReturn(1);

        // act
        var result = createClassUseCase.execute(createClassRequest, userId);

        // assert
        assertThat(result).isNotNull();
        assertThat(result.getCourse()).isEqualTo(courseEntity);
        assertThat(result.getTotalHours()).isEqualTo(800);
        assertThat(result.getShift()).isEqualTo(Shift.FIRST_SHIFT);
        assertThat(result.getId().getCourseId()).isEqualTo(courseId);
        assertThat(result.getId().getNumber()).isEqualTo(1);

        var captor = ArgumentCaptor.forClass(ClassEntity.class);
        verify(classRepository).save(captor.capture());
        var saved = captor.getValue();
        assertThat(saved.getTotalHours()).isEqualTo(800);
        assertThat(saved.getShift()).isEqualTo(Shift.FIRST_SHIFT);
    }

    @DisplayName("Should throw UserNotFoundDomainException when user not found")
    @Test
    void shouldThrowUserNotFoundExceptionWhenUserNotFound() {
        // arrange
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // act and assert
        assertThatThrownBy(() -> createClassUseCase.execute(createClassRequest, userId))
                .isInstanceOf(UserNotFoundDomainException.class);

        verify(classRepository, never()).save(any());
    }

    @DisplayName("Should throw UserWithoutCreateClassPermissionException when user lacks permission")
    @Test
    void shouldThrowPermissionExceptionWhenUserLacksPermission() {
        // arrange
        var regularUser = new UserEntity(userId, null, null, null, RoleUser.USER);
        when(userRepository.findById(userId)).thenReturn(Optional.of(regularUser));
        when(permissionService.canModifyClass(RoleUser.USER)).thenReturn(false);

        // act and assert
        assertThatThrownBy(() -> createClassUseCase.execute(createClassRequest, userId))
                .isInstanceOf(UserWithoutCreateClassPermissionException.class);

        verify(classRepository, never()).save(any());
    }

    @DisplayName("Should throw CourseNotFoundException when course not found")
    @Test
    void shouldThrowCourseNotFoundExceptionWhenCourseNotFound() {
        // arrange
        when(userRepository.findById(userId)).thenReturn(Optional.of(adminUser));
        when(permissionService.canModifyClass(RoleUser.ADMIN)).thenReturn(true);
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // act and assert
        assertThatThrownBy(() -> createClassUseCase.execute(createClassRequest, userId))
                .isInstanceOf(CourseNotFoundException.class);

        verify(classRepository, never()).save(any());
    }

    @DisplayName("Should create class with correct sequential number")
    @Test
    void shouldCreateClassWithCorrectSequentialNumber() {
        // arrange
        when(userRepository.findById(userId)).thenReturn(Optional.of(adminUser));
        when(permissionService.canModifyClass(RoleUser.ADMIN)).thenReturn(true);
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(courseEntity));
        when(classRepository.getNextNumberOfCourse(courseEntity)).thenReturn(5);

        // act
        var result = createClassUseCase.execute(createClassRequest, userId);

        // assert
        assertThat(result.getId().getNumber()).isEqualTo(5);
    }
}
