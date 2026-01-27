package com.api.synco.module.room.domain.use_case;

import com.api.synco.module.room.domain.RoomEntity;
import com.api.synco.module.room.domain.enumerator.TypeRoom;
import com.api.synco.module.room.domain.filter.RoomFilter;
import com.api.synco.module.room.domain.filter.RoomPage;
import com.api.synco.module.room.domain.port.RoomRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class GetAllRoomUseCaseTest {

    @Test
    void execute_shouldBuildFilterAndPage_andDelegateToRepository() {
        RoomRepository roomRepository = mock(RoomRepository.class);
        GetAllRoomUseCase useCase = new GetAllRoomUseCase(roomRepository);

        TypeRoom typeRoom = TypeRoom.LAB_INFORMATICA;
        int number = 101;
        int pageNumber = 0;
        int pageSize = 10;

        Page<RoomEntity> expected = new PageImpl<>(List.of(mock(RoomEntity.class)));
        when(roomRepository.findAll(any(RoomFilter.class), any(RoomPage.class))).thenReturn(expected);

        Page<RoomEntity> result = useCase.execute(typeRoom, number, pageNumber, pageSize);

        assertThat(result).isSameAs(expected);

        ArgumentCaptor<RoomFilter> filterCaptor = ArgumentCaptor.forClass(RoomFilter.class);
        ArgumentCaptor<RoomPage> pageCaptor = ArgumentCaptor.forClass(RoomPage.class);

        verify(roomRepository).findAll(filterCaptor.capture(), pageCaptor.capture());

        RoomFilter usedFilter = filterCaptor.getValue();
        RoomPage usedPage = pageCaptor.getValue();

        assertThat(usedFilter.typeRoom()).isEqualTo(typeRoom);
        assertThat(usedFilter.number()).isEqualTo(number);

        assertThat(usedPage.pageNumber()).isEqualTo(pageNumber);
        assertThat(usedPage.pageSize()).isEqualTo(pageSize);

        verifyNoMoreInteractions(roomRepository);
    }

}
