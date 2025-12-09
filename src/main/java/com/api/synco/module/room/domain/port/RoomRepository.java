package com.api.synco.module.room.domain.port;

import com.api.synco.module.room.domain.RoomEntity;

public interface RoomRepository {

    boolean existByNumber(int number);

    void save(RoomEntity room);
}
