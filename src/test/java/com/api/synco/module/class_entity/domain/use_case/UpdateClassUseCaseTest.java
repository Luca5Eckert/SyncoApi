package com.api.synco.module.class_entity.domain.use_case;

import com.api.synco.module.class_entity.application.dto.update.UpdateClassRequest;
import com.api.synco.module.class_entity.domain.ClassEntity;
import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.class_entity.domain.enumerator.Shift;
import com.api.synco.module.class_entity.domain.exception.ClassNotFoundException;
import com.api.synco.module.class_entity.domain.exception.user.UserWithoutUpdateClassPermissionException;
import com.api.synco.module.class_entity.domain.port.ClassRepository;
import com.api.synco.module.course.domain.CourseEntity;
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
class UpdateClassUseCaseTest {

    @Mock
    private PermissionService permissionService;

    @Mock
    private ClassRepository classRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UpdateClassUseCase updateClassUseCase;

    private long userId;
    private ClassEntityId classEntityId;
    private UserEntity adminUser;
    private CourseEntity courseEntity;
    private ClassEntity existingClass;
    private UpdateClassRequest updateRequest;

    @BeforeEach
    void setup() {
        userId = 1L;
        classEntityId = new ClassEntityId(10L, 1);
        adminUser = new UserEntity(userId, null, null, null, RoleUser.ADMIN);
        courseEntity = new CourseEntity(10L, "Computer Science", "CS", "CS Description");
        existingClass = new ClassEntity(classEntityId, courseEntity, 800, Shift.FIRST_SHIFT);
        updateRequest = new UpdateClassRequest(1000, Shift.SECOND_SHIFT);
    }

    @DisplayName("Should update class successfully when user has permission")
    @Test
    void shouldUpdateClassSuccessfully() {
        // arrange
        when(userRepository.findById(userId)).thenReturn(Optional.of(adminUser));
        when(permissionService.canModifyClass(RoleUser.ADMIN)).thenReturn(true);
        when(classRepository.findById(classEntityId)).thenReturn(Optional.of(existingClass));

        // act
        var result = updateClassUseCase.execute(updateRequest, classEntityId, userId);

        // assert
        assertThat(result).isNotNull();
        assertThat(result.getTotalHours()).isEqualTo(1000);
        assertThat(result.getShift()).isEqualTo(Shift.SECOND_SHIFT);

        var captor = ArgumentCaptor.forClass(ClassEntity.class);
        verify(classRepository).save(captor.capture());
        var saved = captor.getValue();
        assertThat(saved.getTotalHours()).isEqualTo(1000);
        assertThat(saved.getShift()).isEqualTo(Shift.SECOND_SHIFT);
    }

    @DisplayName("Should throw UserNotFoundDomainException when user not found")
    @Test
    void shouldThrowUserNotFoundExceptionWhenUserNotFound() {
        // arrange
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // act and assert
        assertThatThrownBy(() -> updateClassUseCase.execute(updateRequest, classEntityId, userId))
                .isInstanceOf(UserNotFoundDomainException.class);

        verify(classRepository, never()).findById(any());
        verify(classRepository, never()).save(any());
    }

    @DisplayName("Should throw UserWithoutUpdateClassPermissionException when user lacks permission")
    @Test
    void shouldThrowPermissionExceptionWhenUserLacksPermission() {
        // arrange
        var regularUser = new UserEntity(userId, null, null, null, RoleUser.USER);
        when(userRepository.findById(userId)).thenReturn(Optional.of(regularUser));
        when(permissionService.canModifyClass(RoleUser.USER)).thenReturn(false);

        // act and assert
        assertThatThrownBy(() -> updateClassUseCase.execute(updateRequest, classEntityId, userId))
                .isInstanceOf(UserWithoutUpdateClassPermissionException.class);

        verify(classRepository, never()).findById(any());
        verify(classRepository, never()).save(any());
    }

    @DisplayName("Should throw ClassNotFoundException when class not found")
    @Test
    void shouldThrowClassNotFoundExceptionWhenClassNotFound() {
        // arrange
        when(userRepository.findById(userId)).thenReturn(Optional.of(adminUser));
        when(permissionService.canModifyClass(RoleUser.ADMIN)).thenReturn(true);
        when(classRepository.findById(classEntityId)).thenReturn(Optional.empty());

        // act and assert
        assertThatThrownBy(() -> updateClassUseCase.execute(updateRequest, classEntityId, userId))
                .isInstanceOf(ClassNotFoundException.class);

        verify(classRepository, never()).save(any());
    }

    @DisplayName("Should update only total hours while keeping shift")
    @Test
    void shouldUpdateTotalHoursCorrectly() {
        // arrange
        var updateHoursOnly = new UpdateClassRequest(500, Shift.FIRST_SHIFT);
        when(userRepository.findById(userId)).thenReturn(Optional.of(adminUser));
        when(permissionService.canModifyClass(RoleUser.ADMIN)).thenReturn(true);
        when(classRepository.findById(classEntityId)).thenReturn(Optional.of(existingClass));

        // act
        var result = updateClassUseCase.execute(updateHoursOnly, classEntityId, userId);

        // assert
        assertThat(result.getTotalHours()).isEqualTo(500);
        assertThat(result.getShift()).isEqualTo(Shift.FIRST_SHIFT);
    }
}
