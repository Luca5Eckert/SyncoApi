package com.api.synco.module.room.domain.use_case;

import com.api.synco.module.room.domain.RoomEntity;
import com.api.synco.module.room.domain.filter.RoomFilter;
import com.api.synco.module.room.domain.filter.RoomPage;
import com.api.synco.module.room.domain.port.RoomRepository;
import org.springframework.data.domain.Page;

public class GetAllRoomUseCase {

    private final RoomRepository roomRepository;

    public GetAllRoomUseCase(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Page<RoomEntity> execute(RoomFilter roomFilter, RoomPage roomPage){
        return roomRepository.findAll(roomFilter, roomPage);
    }

}
