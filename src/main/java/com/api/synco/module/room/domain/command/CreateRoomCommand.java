package com.api.synco.module.room.domain.command;

import com.api.synco.module.room.domain.enumerator.TypeRoom;

public record CreateRoomCommand(
        int number,
        TypeRoom typeRoom,
        long authenticatedUserId
) {
}
