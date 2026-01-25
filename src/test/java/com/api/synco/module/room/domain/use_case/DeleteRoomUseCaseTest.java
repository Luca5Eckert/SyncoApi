package com.api.synco.module.room.domain.use_case;

import com.api.synco.module.room.domain.exception.user.UserWithoutDeleteRoomPermissionException;
import com.api.synco.module.room.domain.permission.RoomPermissionPolicy;
import com.api.synco.module.room.domain.port.RoomRepository;
import com.api.synco.module.user.domain.UserEntity;
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
class DeleteRoomUseCaseTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoomPermissionPolicy roomPermissionPolicy;

    @InjectMocks
    private DeleteRoomUseCase deleteRoomUseCase;

    @Test
    @DisplayName("Should delete room successfully when user exists and has permission")
    void shouldDeleteRoomSuccessfully() {
        // Arrange
        long roomId = 100L;
        long userId = 1L;
        UserEntity userMock = mock(UserEntity.class);

        when(userRepository.findById(userId)).thenReturn(Optional.of(userMock));
        when(roomPermissionPolicy.canDelete(any())).thenReturn(true);

        // Act
        deleteRoomUseCase.execute(roomId, userId);

        // Assert
        verify(userRepository).findById(userId);
        verify(roomPermissionPolicy).canDelete(any());
        verify(roomRepository).deleteById(roomId);
    }

    @Test
    @DisplayName("Should throw UserNotFoundDomainException when user does not exist")
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        long roomId = 100L;
        long userId = 99L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundDomainException.class,
                () -> deleteRoomUseCase.execute(roomId, userId));

        verify(userRepository).findById(userId);
        verifyNoInteractions(roomPermissionPolicy);
        verifyNoInteractions(roomRepository);
    }

    @Test
    @DisplayName("Should throw UserWithoutDeleteRoomPermissionException when user lacks permission")
    void shouldThrowExceptionWhenUserHasNoPermission() {
        // Arrange
        long roomId = 100L;
        long userId = 1L;
        UserEntity userMock = mock(UserEntity.class);

        when(userRepository.findById(userId)).thenReturn(Optional.of(userMock));
        when(roomPermissionPolicy.canDelete(any())).thenReturn(false);

        // Act & Assert
        assertThrows(UserWithoutDeleteRoomPermissionException.class,
                () -> deleteRoomUseCase.execute(roomId, userId));

        verify(userRepository).findById(userId);
        verify(roomPermissionPolicy).canDelete(any());
        verify(roomRepository, never()).deleteById(anyLong());
    }

}