package com.api.synco.module.room_verification.infrastructure.persistence.repository;

import com.api.synco.module.room_verification.domain.RoomVerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomVerificationRepositoryJpa extends JpaRepository<RoomVerificationEntity, Long> {
    boolean existsByPeriodId(long periodId);
}
