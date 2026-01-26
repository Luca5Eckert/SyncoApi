package com.api.synco.module.room.domain.command;

import com.api.synco.module.room.domain.enumerator.TypeRoom;

public record UpdateRoomCommand(
        long roomId,
        long authenticatedUserId,
        int number,
        TypeRoom typeRoom
) {
}

