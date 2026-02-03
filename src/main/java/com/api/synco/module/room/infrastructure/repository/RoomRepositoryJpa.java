package com.api.synco.module.room.infrastructure.repository;

import com.api.synco.module.room.domain.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepositoryJpa extends JpaRepository<RoomEntity, Long>, JpaSpecificationExecutor<RoomEntity> {
    boolean existsByNumber(int number);
}
