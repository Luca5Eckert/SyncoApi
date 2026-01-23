package com.api.synco.module.room.infrasctuture.repository;

import com.api.synco.module.room.domain.RoomEntity;
import com.api.synco.module.room.domain.port.RoomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoomRepositoryJpa extends JpaRepository<RoomEntity, UUID>{
    boolean existsByNumber(int number);
}
