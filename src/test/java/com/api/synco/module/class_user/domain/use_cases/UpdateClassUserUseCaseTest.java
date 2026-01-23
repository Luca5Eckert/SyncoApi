package com.api.synco.module.class_user.domain.use_cases;

import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.class_entity.domain.exception.ClassNotFoundException;
import com.api.synco.module.class_user.application.dto.update.UpdateClassUserRequest;
import com.api.synco.module.class_user.domain.ClassUser;
import com.api.synco.module.class_user.domain.ClassUserId;
import com.api.synco.module.class_user.domain.enumerator.TypeUserClass;
import com.api.synco.module.class_user.domain.exception.user.UserWithoutCreateClassUserPermissionException;
import com.api.synco.module.class_user.domain.port.ClassUserRepository;
import com.api.synco.module.permission.domain.policies.PermissionPolicy;
import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.enumerator.RoleUser;
import com.api.synco.module.user.domain.exception.UserNotFoundDomainException;
import com.api.synco.module.user.domain.port.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateClassUserUseCaseTest {

    @InjectMocks
    private UpdateClassUserUseCase updateClassUserUseCase;

    @Mock
    private ClassUserRepository classUserRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PermissionPolicy permissionPolicy;

    // Variáveis para compor os cenários
    private final long AUTH_USER_ID = 1L;
    private final long TARGET_USER_ID = 50L;
    private ClassUserId classUserId;

    @BeforeEach
    void setUp() {
        long courseId = 10L;
        int classNumber = 20241;

        ClassEntityId classEntityId = new ClassEntityId(courseId, classNumber);
        this.classUserId = new ClassUserId(TARGET_USER_ID, classEntityId);
    }

    @Test
    @DisplayName("Should successfully update ClassUser when all conditions are met")
    void shouldUpdateClassUserSuccessfully() {
        // Arrange
        UpdateClassUserRequest request = new UpdateClassUserRequest(TypeUserClass.TEACHER);

        // 1. Mock do Usuário Autenticado (Admin)
        UserEntity mockAdmin = mock(UserEntity.class);
        when(mockAdmin.getRole()).thenReturn(RoleUser.ADMIN);
        when(userRepository.findById(AUTH_USER_ID)).thenReturn(Optional.of(mockAdmin));

        // 2. Mock da Permissão
        when(permissionPolicy.canEdit(RoleUser.ADMIN)).thenReturn(true);

        // 3. Mock do ClassUser alvo (que será atualizado)
        ClassUser mockClassUser = mock(ClassUser.class);
        when(classUserRepository.findById(classUserId)).thenReturn(Optional.of(mockClassUser));

        // Act
        ClassUser result = updateClassUserUseCase.execute(request, classUserId, AUTH_USER_ID);

        // Assert
        assertThat(result).isNotNull();

        verify(mockClassUser).update(TypeUserClass.TEACHER);

        verify(classUserRepository).save(mockClassUser);
    }

    @Test
    @DisplayName("Should throw UserNotFoundDomainException when authenticated user does not exist")
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        UpdateClassUserRequest request = new UpdateClassUserRequest(TypeUserClass.STUDENT);

        // Simula que o usuário logado não foi encontrado
        when(userRepository.findById(AUTH_USER_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> updateClassUserUseCase.execute(request, classUserId, AUTH_USER_ID))
                .isInstanceOf(UserNotFoundDomainException.class);

        verifyNoInteractions(permissionPolicy);
        verifyNoInteractions(classUserRepository);
    }

    @Test
    @DisplayName("Should throw UserWithoutCreateClassUserPermissionException when user has no permission")
    void shouldThrowExceptionWhenUserHasNoPermission() {
        // Arrange
        UpdateClassUserRequest request = new UpdateClassUserRequest(TypeUserClass.REPRESENTATIVE);

        UserEntity mockUser = mock(UserEntity.class);
        when(mockUser.getRole()).thenReturn(RoleUser.USER);
        when(userRepository.findById(AUTH_USER_ID)).thenReturn(Optional.of(mockUser));

        when(permissionPolicy.canEdit(RoleUser.USER)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> updateClassUserUseCase.execute(request, classUserId, AUTH_USER_ID))
                .isInstanceOf(UserWithoutCreateClassUserPermissionException.class);

        verifyNoInteractions(classUserRepository);
    }

    @Test
    @DisplayName("Should throw ClassNotFoundException when ClassUser target does not exist")
    void shouldThrowExceptionWhenClassUserNotFound() {
        // Arrange
        UpdateClassUserRequest request = new UpdateClassUserRequest(TypeUserClass.ADMINISTRATOR);

        UserEntity mockAdmin = mock(UserEntity.class);
        when(mockAdmin.getRole()).thenReturn(RoleUser.ADMIN);
        when(userRepository.findById(AUTH_USER_ID)).thenReturn(Optional.of(mockAdmin));

        when(permissionPolicy.canEdit(RoleUser.ADMIN)).thenReturn(true);

        when(classUserRepository.findById(classUserId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> updateClassUserUseCase.execute(request, classUserId, AUTH_USER_ID))
                .isInstanceOf(ClassNotFoundException.class);

        verify(classUserRepository, never()).save(any());
    }
}