package com.api.synco.module.room.domain.filter;

import com.api.synco.module.room.domain.enumerator.TypeRoom;

public record RoomFilter(
        TypeRoom typeRoom,
        int number
) {
}
