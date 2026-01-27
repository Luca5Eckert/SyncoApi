package com.api.synco.module.room.application.mapper;

import com.api.synco.module.room.application.dto.RoomResponse;
import com.api.synco.module.room.domain.RoomEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {

    public RoomResponse of(RoomEntity entity) {
        return new RoomResponse(
                entity.getId(),
                entity.getNumber(),
                entity.getTypeRoom()
        );
    }

    public Page<RoomResponse> of(Page<RoomEntity> page) {
        return page.map(this::of);
    }
}
