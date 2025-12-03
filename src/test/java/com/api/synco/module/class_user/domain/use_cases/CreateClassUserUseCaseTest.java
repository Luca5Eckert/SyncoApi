package com.api.synco.module.class_user.domain.use_cases;

import com.api.synco.module.class_entity.domain.ClassEntity;
import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.class_entity.domain.exception.ClassNotFoundException;
import com.api.synco.module.class_entity.domain.port.ClassRepository;
import com.api.synco.module.class_user.application.dto.create.CreateClassUserRequest;
import com.api.synco.module.class_user.domain.ClassUser;
import com.api.synco.module.class_user.domain.enumerator.TypeUserClass;
import com.api.synco.module.class_user.domain.exception.user.UserWithoutCreateClassUserPermissionException;
import com.api.synco.module.class_user.domain.port.ClassUserRepository;
import com.api.synco.module.permission.domain.service.PermissionService;
import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.enumerator.RoleUser;
import com.api.synco.module.user.domain.exception.UserNotFoundDomainException;
import com.api.synco.module.user.domain.port.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateClassUserUseCaseTest {

    @Mock
    private ClassUserRepository classUserRepository;

    @Mock
    private ClassRepository classRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PermissionService permissionService;

    @InjectMocks
    private CreateClassUserUseCase createClassUserUseCase;

    // Test Data Constants
    private final long AUTH_USER_ID = 1L;
    private final long TARGET_USER_ID = 2L;
    private final long COURSE_ID = 100L;
    private final int CLASS_NUMBER = 10;
    private final RoleUser ROLE_ADMIN = RoleUser.ADMIN;
    private final RoleUser ROLE_STUDENT = RoleUser.USER;

    @Test
    @DisplayName("Should successfully create class user when admin has permission")
    void shouldCreateClassUser_WhenPermissionGranted() {
        // GIVEN
        CreateClassUserRequest request = new CreateClassUserRequest(
                TARGET_USER_ID, COURSE_ID, CLASS_NUMBER, TypeUserClass.STUDENT
        );

        UserEntity adminUser = mock(UserEntity.class);
        UserEntity targetUser = mock(UserEntity.class);
        ClassEntity classEntity = mock(ClassEntity.class);

        // 1. Mock finding the authenticated admin
        when(userRepository.findById(AUTH_USER_ID)).thenReturn(Optional.of(adminUser));
        when(adminUser.getRole()).thenReturn(ROLE_ADMIN);

        // 2. Mock permission check (MUST BE TRUE)
        when(permissionService.canModifyClassUser(ROLE_ADMIN)).thenReturn(true);

        // 3. Mock finding the target user
        when(userRepository.findById(TARGET_USER_ID)).thenReturn(Optional.of(targetUser));

        // 4. Mock finding the class
        when(classRepository.findById(any(ClassEntityId.class))).thenReturn(Optional.of(classEntity));

        // WHEN
        ClassUser result = createClassUserUseCase.execute(request, AUTH_USER_ID);

        // THEN
        assertNotNull(result);
        assertEquals(targetUser, result.getUserEntity());
        assertEquals(classEntity, result.getClassEntity());

        // Verify save was called exactly once
        verify(classUserRepository, times(1)).save(any(ClassUser.class));
    }

    @Test
    @DisplayName("Should throw exception when authenticated user lacks permission")
    void shouldThrowException_WhenNoPermission() {
        // GIVEN
        CreateClassUserRequest request = new CreateClassUserRequest(
                TARGET_USER_ID, COURSE_ID, CLASS_NUMBER, TypeUserClass.STUDENT
        );
        UserEntity regularUser = mock(UserEntity.class);

        when(userRepository.findById(AUTH_USER_ID)).thenReturn(Optional.of(regularUser));
        when(regularUser.getRole()).thenReturn(ROLE_STUDENT);

        // CRITICAL: Permission check returns FALSE
        when(permissionService.canModifyClassUser(ROLE_STUDENT)).thenReturn(false);

        // WHEN & THEN
        assertThrows(UserWithoutCreateClassUserPermissionException.class, () ->
                createClassUserUseCase.execute(request, AUTH_USER_ID)
        );

        // Verify we never attempted to save or look up the class
        verify(classUserRepository, never()).save(any());
        verify(classRepository, never()).findById(any(ClassEntityId.class));
    }

    @Test
    @DisplayName("Should throw exception when authenticated user is not found")
    void shouldThrowException_WhenAuthUserNotFound() {
        // GIVEN
        CreateClassUserRequest request = new CreateClassUserRequest(
                TARGET_USER_ID, COURSE_ID, CLASS_NUMBER, TypeUserClass.STUDENT
        );
        when(userRepository.findById(AUTH_USER_ID)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(UserNotFoundDomainException.class, () ->
                createClassUserUseCase.execute(request, AUTH_USER_ID)
        );
    }

    @Test
    @DisplayName("Should throw exception when class entity is not found")
    void shouldThrowException_WhenClassNotFound() {
        // GIVEN
        CreateClassUserRequest request = new CreateClassUserRequest(
                TARGET_USER_ID, COURSE_ID, CLASS_NUMBER, TypeUserClass.STUDENT
        );
        UserEntity adminUser = mock(UserEntity.class);
        UserEntity targetUser = mock(UserEntity.class);

        when(userRepository.findById(AUTH_USER_ID)).thenReturn(Optional.of(adminUser));
        when(adminUser.getRole()).thenReturn(ROLE_ADMIN);
        when(permissionService.canModifyClassUser(ROLE_ADMIN)).thenReturn(true);
        when(userRepository.findById(TARGET_USER_ID)).thenReturn(Optional.of(targetUser));

        when(classRepository.findById(any(ClassEntityId.class))).thenReturn(Optional.empty());

        assertThrows(ClassNotFoundException.class, () ->
                createClassUserUseCase.execute(request, AUTH_USER_ID)
        );
    }
}