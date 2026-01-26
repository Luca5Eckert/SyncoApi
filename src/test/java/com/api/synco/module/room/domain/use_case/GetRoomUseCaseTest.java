package com.api.synco.module.room.domain.use_case;

import com.api.synco.module.room.domain.RoomEntity;
import com.api.synco.module.room.domain.exception.RoomNotExistException;
import com.api.synco.module.room.domain.port.RoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetRoomUseCaseTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private GetRoomUseCase getRoomUseCase;

    @Test
    @DisplayName("Should return room entity when room exists")
    void shouldReturnRoomWhenExists() {
        // Arrange
        long roomId = 123L;
        RoomEntity roomMock = mock(RoomEntity.class);
        when(roomRepository.findById(roomId)).thenReturn(Optional.of(roomMock));

        // Act
        RoomEntity result = getRoomUseCase.execute(roomId);

        // Assert
        assertNotNull(result);
        assertEquals(roomMock, result);
        verify(roomRepository).findById(roomId);
    }

    @Test
    @DisplayName("Should throw RoomNotExistException when room does not exist")
    void shouldThrowExceptionWhenRoomDoesNotExist() {
        // Arrange
        long roomId = 123L;
        when(roomRepository.findById(roomId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RoomNotExistException.class,
                () -> getRoomUseCase.execute(roomId));

        verify(roomRepository).findById(roomId);
    }
}