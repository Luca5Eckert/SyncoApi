package com.api.synco.module.room_verification.domain.use_case;

import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.class_user.domain.ClassUser;
import com.api.synco.module.class_user.domain.ClassUserId;
import com.api.synco.module.class_user.domain.exception.ClassUserNotFoundException;
import com.api.synco.module.class_user.domain.port.ClassUserRepository;
import com.api.synco.module.room_verification.domain.RoomVerificationEntity;
import com.api.synco.module.room_verification.domain.command.GetRoomVerificationCommand;
import com.api.synco.module.room_verification.domain.exception.RoomVerificationNotFound;
import com.api.synco.module.room_verification.domain.exception.user.UserNotHavePermissionToGetRoomVerificationException;
import com.api.synco.module.room_verification.domain.permission.RoomVerificationPermissionPolicy;
import com.api.synco.module.room_verification.domain.port.RoomVerificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetRoomVerificationUseCaseTest {

    @Mock
    private RoomVerificationRepository roomVerificationRepository;

    @Mock
    private ClassUserRepository classUserRepository;

    @Mock
    private RoomVerificationPermissionPolicy permissionPolicy;

    private GetRoomVerificationUseCase useCase;

    @BeforeEach
    void setup() {
        useCase = new GetRoomVerificationUseCase(roomVerificationRepository, classUserRepository, permissionPolicy);
    }

    @Test
    void execute_shouldReturnEntity_whenExistsAndUserHasPermission() {
        // Arrange
        long roomVerificationId = 10L;
        long userAuthenticatedId = 99L;

        GetRoomVerificationCommand command = mock(GetRoomVerificationCommand.class);
        when(command.roomVerificationId()).thenReturn(roomVerificationId);
        when(command.userAuthenticatedId()).thenReturn(userAuthenticatedId);

        // Deep stub for chained call: entity.getPeriod().getClassEntity().getId()
        RoomVerificationEntity entityDeep = mock(RoomVerificationEntity.class, RETURNS_DEEP_STUBS);
        ClassEntityId classEntityId = mock(ClassEntityId.class);

        when(roomVerificationRepository.findById(roomVerificationId)).thenReturn(Optional.of(entityDeep));
        when(entityDeep.getPeriod().getClassEntity().getId()).thenReturn(classEntityId);

        // Deep stub for chained call: classUser.getUserEntity().getRole()
        ClassUser classUserDeep = mock(ClassUser.class, RETURNS_DEEP_STUBS);
        when(classUserRepository.findById(any(ClassUserId.class))).thenReturn(Optional.of(classUserDeep));

        when(permissionPolicy.canUpdate(any(), any())).thenReturn(true);

        // Act
        RoomVerificationEntity result = useCase.execute(command);

        // Assert
        assertSame(entityDeep, result);

        verify(roomVerificationRepository).findById(roomVerificationId);

        ArgumentCaptor<ClassUserId> idCaptor = ArgumentCaptor.forClass(ClassUserId.class);
        verify(classUserRepository).findById(idCaptor.capture());
        assertNotNull(idCaptor.getValue());

        verify(permissionPolicy).canUpdate(any(), any());
        verifyNoMoreInteractions(roomVerificationRepository, classUserRepository, permissionPolicy);
    }

    @Test
    void execute_shouldThrowRoomVerificationNotFound_whenEntityDoesNotExist() {
        // Arrange
        long roomVerificationId = 404L;

        GetRoomVerificationCommand command = mock(GetRoomVerificationCommand.class);
        when(command.roomVerificationId()).thenReturn(roomVerificationId);

        when(roomVerificationRepository.findById(roomVerificationId)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(RoomVerificationNotFound.class, () -> useCase.execute(command));

        verify(roomVerificationRepository).findById(roomVerificationId);
        verifyNoInteractions(classUserRepository, permissionPolicy);
    }

    @Test
    void execute_shouldThrowClassUserNotFoundException_whenUserIsNotInClass() {
        // Arrange
        long roomVerificationId = 1L;
        long userAuthenticatedId = 2L;

        GetRoomVerificationCommand command = mock(GetRoomVerificationCommand.class);
        when(command.roomVerificationId()).thenReturn(roomVerificationId);
        when(command.userAuthenticatedId()).thenReturn(userAuthenticatedId);

        RoomVerificationEntity entityDeep = mock(RoomVerificationEntity.class, RETURNS_DEEP_STUBS);
        when(roomVerificationRepository.findById(roomVerificationId)).thenReturn(Optional.of(entityDeep));

        // Must exist because validateUserPermissions extracts it
        when(entityDeep.getPeriod().getClassEntity().getId()).thenReturn(mock(ClassEntityId.class));

        when(classUserRepository.findById(any(ClassUserId.class))).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(ClassUserNotFoundException.class, () -> useCase.execute(command));

        verify(roomVerificationRepository).findById(roomVerificationId);
        verify(classUserRepository).findById(any(ClassUserId.class));
        verifyNoInteractions(permissionPolicy);
    }

    @Test
    void execute_shouldThrowUserNotHavePermission_whenPolicyDenies() {
        // Arrange
        long roomVerificationId = 7L;
        long userAuthenticatedId = 8L;

        GetRoomVerificationCommand command = mock(GetRoomVerificationCommand.class);
        when(command.roomVerificationId()).thenReturn(roomVerificationId);
        when(command.userAuthenticatedId()).thenReturn(userAuthenticatedId);

        RoomVerificationEntity entityDeep = mock(RoomVerificationEntity.class, RETURNS_DEEP_STUBS);
        when(roomVerificationRepository.findById(roomVerificationId)).thenReturn(Optional.of(entityDeep));

        // Must exist because validateUserPermissions extracts it
        when(entityDeep.getPeriod().getClassEntity().getId()).thenReturn(mock(ClassEntityId.class));

        // Deep stub prevents NPE on classUser.getUserEntity().getRole()
        ClassUser classUserDeep = mock(ClassUser.class, RETURNS_DEEP_STUBS);
        when(classUserRepository.findById(any(ClassUserId.class))).thenReturn(Optional.of(classUserDeep));

        when(permissionPolicy.canUpdate(any(), any())).thenReturn(false);

        // Act + Assert
        assertThrows(UserNotHavePermissionToGetRoomVerificationException.class, () -> useCase.execute(command));

        verify(roomVerificationRepository).findById(roomVerificationId);
        verify(classUserRepository).findById(any(ClassUserId.class));
        verify(permissionPolicy).canUpdate(any(), any());
    }

    @Test
    void validateUserPermissions_shouldPass_whenUserExistsAndPolicyAllows() {
        // Arrange
        ClassEntityId classEntityId = mock(ClassEntityId.class);
        long userId = 123L;

        ClassUser classUserDeep = mock(ClassUser.class, RETURNS_DEEP_STUBS);
        when(classUserRepository.findById(any(ClassUserId.class))).thenReturn(Optional.of(classUserDeep));

        when(permissionPolicy.canUpdate(any(), any())).thenReturn(true);

        // Act + Assert
        assertDoesNotThrow(() -> useCase.validateUserPermissions(classEntityId, userId));

        verify(classUserRepository).findById(any(ClassUserId.class));
        verify(permissionPolicy).canUpdate(any(), any());
    }
}
