package com.api.synco.module.room.domain.use_case;

import com.api.synco.module.permission.domain.policies.PermissionPolicy;
import com.api.synco.module.room.domain.RoomEntity;
import com.api.synco.module.room.domain.command.UpdateRoomCommand;
import com.api.synco.module.room.domain.enumerator.TypeRoom;
import com.api.synco.module.room.domain.exception.RoomNotExistException;
import com.api.synco.module.room.domain.exception.user.UserWithoutUpdateRoomPermissionException;
import com.api.synco.module.room.domain.port.RoomRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateRoomUseCaseTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PermissionPolicy permissionPolicy;

    @InjectMocks
    private UpdateRoomUseCase updateRoomUseCase;

    @Test
    @DisplayName("Should update room successfully when user exists, has permission and room exists")
    void shouldUpdateRoomSuccessfully() {
        // Arrange
        long userId = 1L;
        long roomId = 100L;
        int newNumber = 202;
        TypeRoom newType = TypeRoom.LAB_INFORMATICA;
        RoleUser role = RoleUser.ADMIN;

        // Mocks
        UpdateRoomCommand command = mock(UpdateRoomCommand.class);
        UserEntity user = mock(UserEntity.class);
        RoomEntity room = mock(RoomEntity.class);

        when(command.authenticatedUserId()).thenReturn(userId);
        when(command.roomId()).thenReturn(roomId);
        when(command.number()).thenReturn(newNumber);
        when(command.typeRoom()).thenReturn(newType);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(user.getRole()).thenReturn(role);
        when(permissionPolicy.canEdit(role)).thenReturn(true);

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));

        // Act
        updateRoomUseCase.execute(command);

        // Assert
        verify(room).update(newNumber, newType);
        verify(roomRepository).save(room);
    }

    @Test
    @DisplayName("Should throw UserNotFoundDomainException when user does not exist")
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        long userId = 99L;
        UpdateRoomCommand command = mock(UpdateRoomCommand.class);
        when(command.authenticatedUserId()).thenReturn(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundDomainException.class, () -> updateRoomUseCase.execute(command));

        verifyNoInteractions(permissionPolicy);
        verifyNoInteractions(roomRepository);
    }

    @Test
    @DisplayName("Should throw UserWithoutUpdateRoomPermissionException when user has no permission")
    void shouldThrowExceptionWhenUserHasNoPermission() {
        // Arrange
        long userId = 1L;
        RoleUser role = RoleUser.USER;

        UpdateRoomCommand command = mock(UpdateRoomCommand.class);
        UserEntity user = mock(UserEntity.class);

        when(command.authenticatedUserId()).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(user.getRole()).thenReturn(role);
        when(permissionPolicy.canEdit(role)).thenReturn(false);

        // Act & Assert
        assertThrows(UserWithoutUpdateRoomPermissionException.class, () -> updateRoomUseCase.execute(command));

        verifyNoInteractions(roomRepository);
    }

    @Test
    @DisplayName("Should throw RoomNotExistException when room does not exist")
    void shouldThrowExceptionWhenRoomNotFound() {
        // Arrange
        long userId = 1L;
        long roomId = 100L;
        RoleUser role = RoleUser.ADMIN;

        UpdateRoomCommand command = mock(UpdateRoomCommand.class);
        UserEntity user = mock(UserEntity.class);

        when(command.authenticatedUserId()).thenReturn(userId);
        when(command.roomId()).thenReturn(roomId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(user.getRole()).thenReturn(role);
        when(permissionPolicy.canEdit(role)).thenReturn(true);

        when(roomRepository.findById(roomId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RoomNotExistException.class, () -> updateRoomUseCase.execute(command));

        verify(roomRepository, never()).save(any());
    }

}