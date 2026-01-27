package com.api.synco.module.room.application.mapper;

import com.api.synco.module.room.application.dto.RoomResponse;
import com.api.synco.module.room.domain.RoomEntity;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {

    public RoomResponse of(RoomEntity room){
        return new RoomResponse(
                room.getId(),
                room.getNumber(),
                room.getTypeRoom().name()
        );
    }

}
