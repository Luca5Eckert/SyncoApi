package com.api.synco.module.room.domain.use_case;

import com.api.synco.module.room.domain.RoomEntity;
import com.api.synco.module.room.domain.enumerator.TypeRoom;
import com.api.synco.module.room.domain.filter.RoomFilter;
import com.api.synco.module.room.domain.filter.RoomPage;
import com.api.synco.module.room.domain.port.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class GetAllRoomUseCase {

    private final RoomRepository roomRepository;

    public GetAllRoomUseCase(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Page<RoomEntity> execute(
            TypeRoom typeRoom,
            int number,
            int pageNumber,
            int pageSize
    ){

        RoomFilter roomFilter = new RoomFilter(
                typeRoom,
                number
        );

        RoomPage roomPage = new RoomPage(
                pageNumber,
                pageSize
        );

        return roomRepository.findAll(roomFilter, roomPage);
    }

}
