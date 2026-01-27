package com.api.synco.module.room.application.dto;

import com.api.synco.module.room.domain.enumerator.TypeRoom;

public record RoomResponse(
        long id,
        int number,
        TypeRoom typeRoom
) {
}
