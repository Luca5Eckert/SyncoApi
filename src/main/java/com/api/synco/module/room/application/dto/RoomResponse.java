package com.api.synco.module.room.application.dto;

public record RoomResponse(
        long id,
        int number,
        String typeRoom
) {
}
