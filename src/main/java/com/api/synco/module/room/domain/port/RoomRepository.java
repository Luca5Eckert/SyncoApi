package com.api.synco.module.room.domain.port;

import com.api.synco.module.room.domain.RoomEntity;

import java.util.Optional;

public interface RoomRepository {

    boolean existByNumber(int number);

    void save(RoomEntity room);

    void deleteById(long roomId);

    Optional<RoomEntity> findById(long roomId);

    boolean existsById(long roomId);
}
