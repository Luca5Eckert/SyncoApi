package com.api.synco.module.room.infrasctuture.repository;

import com.api.synco.module.room.domain.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepositoryJpa extends JpaRepository<RoomEntity, Long>{
    boolean existsByNumber(int number);
}
