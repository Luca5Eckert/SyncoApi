package com.api.synco.module.room_verification.infrastructure.persistence.repository;

import com.api.synco.module.room_verification.domain.RoomVerificationEntity;
import com.api.synco.module.room_verification.domain.port.RoomVerificationRepository;
import org.springframework.stereotype.Repository;

@Repository
public class RoomVerificationRepositoryAdapter implements RoomVerificationRepository {

    private final RoomVerificationRepositoryJpa roomVerificationRepositoryJpa;

    public RoomVerificationRepositoryAdapter(RoomVerificationRepositoryJpa roomVerificationRepositoryJpa) {
        this.roomVerificationRepositoryJpa = roomVerificationRepositoryJpa;
    }

    @Override
    public boolean existsByPeriodId(long periodId) {
        return roomVerificationRepositoryJpa.existsByPeriodId(periodId);
    }

    @Override
    public void save(RoomVerificationEntity roomVerificationEntity) {
        roomVerificationRepositoryJpa.save(roomVerificationEntity);
    }
}
