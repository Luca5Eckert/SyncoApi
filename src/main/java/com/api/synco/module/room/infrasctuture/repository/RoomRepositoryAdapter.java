package com.api.synco.module.room.infrasctuture.repository;

import com.api.synco.module.room.domain.RoomEntity;
import com.api.synco.module.room.domain.port.RoomRepository;
import org.springframework.stereotype.Repository;

@Repository
public class RoomRepositoryAdapter implements RoomRepository {

    private final RoomRepositoryJpa roomRepositoryJpa;

    public RoomRepositoryAdapter(RoomRepositoryJpa roomRepositoryJpa) {
        this.roomRepositoryJpa = roomRepositoryJpa;
    }

    @Override
    public boolean existByNumber(int number) {
        return roomRepositoryJpa.existsByNumber(number);
    }

    @Override
    public void save(RoomEntity room) {
        roomRepositoryJpa.save(room);
    }

    @Override
    public void deleteById(long roomId) {
        roomRepositoryJpa.deleteById(roomId);
    }

}
