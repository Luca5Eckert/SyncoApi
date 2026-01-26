package com.api.synco.module.room.domain.use_case;

import com.api.synco.module.room.domain.RoomEntity;
import com.api.synco.module.room.domain.filter.RoomFilter;
import com.api.synco.module.room.domain.filter.RoomPage;
import com.api.synco.module.room.domain.port.RoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllRoomUseCaseTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private GetAllRoomUseCase getAllRoomUseCase;

    @Test
    @DisplayName("Should return page of rooms when executed with valid filter and page")
    void shouldReturnPageOfRooms() {
        // Arrange
        RoomFilter filterMock = mock(RoomFilter.class);
        RoomPage pageMock = mock(RoomPage.class);

        @SuppressWarnings("unchecked")
        Page<RoomEntity> expectedPage = mock(Page.class);

        when(roomRepository.findAll(filterMock, pageMock)).thenReturn(expectedPage);

        // Act
        Page<RoomEntity> result = getAllRoomUseCase.execute(filterMock, pageMock);

        // Assert
        assertEquals(expectedPage, result);
        verify(roomRepository).findAll(filterMock, pageMock);
    }

}