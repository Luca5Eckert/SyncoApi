package com.api.synco.module.room.application.dto;

import com.api.synco.module.room.domain.enumerator.TypeRoom;

public record CreateRoomRequest(
        int number,
        TypeRoom typeRoom
) {
}
