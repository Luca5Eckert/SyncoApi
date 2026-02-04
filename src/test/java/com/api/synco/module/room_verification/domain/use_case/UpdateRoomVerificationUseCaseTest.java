package com.api.synco.module.room_verification.domain.use_case;

import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.class_user.domain.ClassUser;
import com.api.synco.module.class_user.domain.ClassUserId;
import com.api.synco.module.class_user.domain.enumerator.TypeUserClass;
import com.api.synco.module.class_user.domain.exception.ClassUserNotFoundException;
import com.api.synco.module.class_user.domain.port.ClassUserRepository;
import com.api.synco.module.room_verification.domain.RoomVerificationEntity;
import com.api.synco.module.room_verification.domain.command.UpdateRoomVerificationCommand;
import com.api.synco.module.room_verification.domain.exception.RoomVerificationNotFound;
import com.api.synco.module.room_verification.domain.exception.user.UserNotHavePermissionToCreateRoomVerificationException;
import com.api.synco.module.room_verification.domain.permission.RoomVerificationPermissionPolicy;
import com.api.synco.module.room_verification.domain.port.RoomVerificationRepository;
import com.api.synco.module.user.domain.UserEntity;

import com.api.synco.module.user.domain.enumerator.RoleUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateRoomVerificationUseCaseTest {

    @Mock
    private RoomVerificationRepository roomVerificationRepository;

    @Mock
    private ClassUserRepository classUserRepository;

    @Mock
    private RoomVerificationPermissionPolicy permissionPolicy;

    @InjectMocks
    private UpdateRoomVerificationUseCase useCase;

    @Test
    void execute_shouldUpdateAndReturnEntity_whenEverythingIsValid() {
        // Arrange
        UpdateRoomVerificationCommand command = mock(UpdateRoomVerificationCommand.class);

        long userId = 10L;
        ClassEntityId classEntityId = mock(ClassEntityId.class);

        RoomVerificationEntity entity =
                mock(RoomVerificationEntity.class, RETURNS_DEEP_STUBS);

        ClassUser classUser = mock(ClassUser.class);
        UserEntity userEntity = mock(UserEntity.class);

        when(command.roomVerificationId()).thenReturn(1L);
        when(command.userAuthenticatedId()).thenReturn(userId);
        when(command.allOrganized()).thenReturn(true);
        when(command.description()).thenReturn("desc");
        when(command.observations()).thenReturn("obs");
        when(command.ticket()).thenReturn("ticket");

        when(roomVerificationRepository.findById(1L))
                .thenReturn(Optional.of(entity));

        when(entity.getPeriod()
                .getClassEntity()
                .getId())
                .thenReturn(classEntityId);

        when(classUserRepository.findById(any(ClassUserId.class)))
                .thenReturn(Optional.of(classUser));

        when(classUser.getUserEntity()).thenReturn(userEntity);

        when(userEntity.getRole()).thenReturn(mock(RoleUser.class));
        when(classUser.getTypeUserClass()).thenReturn(mock(TypeUserClass.class));

        when(permissionPolicy.canUpdate(any(), any()))
                .thenReturn(true);

        // Act
        RoomVerificationEntity result = useCase.execute(command);

        // Assert
        verify(entity).update(true, "desc", "obs", "ticket");
        verify(roomVerificationRepository).save(entity);
        assertEquals(entity, result);
    }

    @Test
    void execute_shouldThrowException_whenRoomVerificationNotFound() {
        // Arrange
        UpdateRoomVerificationCommand command = mock(UpdateRoomVerificationCommand.class);

        when(command.roomVerificationId()).thenReturn(1L);
        when(roomVerificationRepository.findById(1L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RoomVerificationNotFound.class,
                () -> useCase.execute(command));
    }

    @Test
    void execute_shouldThrowException_whenClassUserNotFound() {
        // Arrange
        UpdateRoomVerificationCommand command = mock(UpdateRoomVerificationCommand.class);

        long userId = 10L;
        ClassEntityId classEntityId = mock(ClassEntityId.class);

        RoomVerificationEntity entity =
                mock(RoomVerificationEntity.class, RETURNS_DEEP_STUBS);

        when(command.roomVerificationId()).thenReturn(1L);
        when(command.userAuthenticatedId()).thenReturn(userId);

        when(roomVerificationRepository.findById(1L))
                .thenReturn(Optional.of(entity));

        when(entity.getPeriod()
                .getClassEntity()
                .getId())
                .thenReturn(classEntityId);

        when(classUserRepository.findById(any(ClassUserId.class)))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ClassUserNotFoundException.class,
                () -> useCase.execute(command));
    }

    @Test
    void execute_shouldThrowException_whenUserHasNoPermission() {
        // Arrange
        UpdateRoomVerificationCommand command = mock(UpdateRoomVerificationCommand.class);

        long userId = 10L;
        ClassEntityId classEntityId = mock(ClassEntityId.class);

        RoomVerificationEntity entity =
                mock(RoomVerificationEntity.class, RETURNS_DEEP_STUBS);

        ClassUser classUser = mock(ClassUser.class);
        UserEntity userEntity = mock(UserEntity.class);

        when(command.roomVerificationId()).thenReturn(1L);
        when(command.userAuthenticatedId()).thenReturn(userId);

        when(roomVerificationRepository.findById(1L))
                .thenReturn(Optional.of(entity));

        when(entity.getPeriod()
                .getClassEntity()
                .getId())
                .thenReturn(classEntityId);

        when(classUserRepository.findById(any(ClassUserId.class)))
                .thenReturn(Optional.of(classUser));

        when(classUser.getUserEntity()).thenReturn(userEntity);

        when(userEntity.getRole()).thenReturn(mock(RoleUser.class));
        when(classUser.getTypeUserClass()).thenReturn(mock(TypeUserClass.class));

        when(permissionPolicy.canUpdate(any(), any()))
                .thenReturn(false);

        // Act & Assert
        assertThrows(
                UserNotHavePermissionToCreateRoomVerificationException.class,
                () -> useCase.execute(command)
        );
    }
}
