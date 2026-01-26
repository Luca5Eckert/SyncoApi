package com.api.synco.module.room.domain.use_case;

import com.api.synco.module.room.domain.RoomEntity;
import com.api.synco.module.room.domain.exception.RoomNotExistException;
import com.api.synco.module.room.domain.port.RoomRepository;
import org.springframework.stereotype.Component;

@Component
public class GetRoomUseCase {

    private final RoomRepository roomRepository;

    public GetRoomUseCase(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public RoomEntity execute(long roomId){
        return roomRepository.findById(roomId)
                .orElseThrow( () -> new RoomNotExistException(roomId));
    }

}
