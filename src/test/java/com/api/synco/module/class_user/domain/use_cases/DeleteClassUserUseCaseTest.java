package com.api.synco.module.class_user.domain.use_cases;

import com.api.synco.module.class_user.domain.ClassUserId;
import com.api.synco.module.class_user.domain.exception.ClassUserNotFoundException;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteClassUserUseCaseTest {

    @Mock
    private ClassUserRepository classUserRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PermissionService permissionService;

    @InjectMocks
    private DeleteClassUserUseCase deleteClassUserUseCase;

    private final long AUTH_USER_ID = 1L;
    private final RoleUser ROLE_ADMIN = RoleUser.ADMIN;

    @Mock
    private ClassUserId classUserId;

    @Test
    @DisplayName("Should successfully delete class user when authorized and entity exists")
    void shouldDelete_WhenAuthorizedAndExists() {
        UserEntity admin = mock(UserEntity.class);
        when(userRepository.findById(AUTH_USER_ID)).thenReturn(Optional.of(admin));
        when(admin.getRole()).thenReturn(ROLE_ADMIN);

        // Permission granted
        when(permissionService.canModifyClassUser(ROLE_ADMIN)).thenReturn(true);

        // Entity exists
        when(classUserRepository.existById(classUserId)).thenReturn(true);

        // WHEN
        deleteClassUserUseCase.execute(classUserId, AUTH_USER_ID);

        // THEN
        verify(classUserRepository, times(1)).deleteById(classUserId);
    }

    @Test
    @DisplayName("Should throw exception when authenticated user not found")
    void shouldThrow_WhenAuthUserNotFound() {
        // GIVEN
        when(userRepository.findById(AUTH_USER_ID)).thenReturn(Optional.empty());

        // WHEN/THEN
        assertThrows(UserNotFoundDomainException.class, () ->
                deleteClassUserUseCase.execute(classUserId, AUTH_USER_ID)
        );

        verify(classUserRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Should throw exception when user lacks permission")
    void shouldThrow_WhenNoPermission() {
        // GIVEN
        UserEntity user = mock(UserEntity.class);
        when(userRepository.findById(AUTH_USER_ID)).thenReturn(Optional.of(user));
        when(user.getRole()).thenReturn(RoleUser.USER); // No permission role

        // Permission denied
        when(permissionService.canModifyClassUser(RoleUser.USER)).thenReturn(false);

        // WHEN/THEN
        assertThrows(UserWithoutCreateClassUserPermissionException.class, () ->
                deleteClassUserUseCase.execute(classUserId, AUTH_USER_ID)
        );

        verify(classUserRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Should throw exception when ClassUser relationship does not exist")
    void shouldThrow_WhenClassUserNotFound() {
        // GIVEN
        UserEntity admin = mock(UserEntity.class);
        when(userRepository.findById(AUTH_USER_ID)).thenReturn(Optional.of(admin));
        when(admin.getRole()).thenReturn(ROLE_ADMIN);
        when(permissionService.canModifyClassUser(ROLE_ADMIN)).thenReturn(true);

        // Entity does NOT exist
        when(classUserRepository.existById(classUserId)).thenReturn(false);

        // WHEN/THEN
        assertThrows(ClassUserNotFoundException.class, () ->
                deleteClassUserUseCase.execute(classUserId, AUTH_USER_ID)
        );

        verify(classUserRepository, never()).deleteById(any());
    }
}