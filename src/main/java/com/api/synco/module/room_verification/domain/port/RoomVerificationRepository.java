package com.api.synco.module.room_verification.domain.port;

import com.api.synco.module.room_verification.domain.RoomVerificationEntity;

import java.util.Optional;

public interface RoomVerificationRepository {
    boolean existsByPeriodId(long periodId);

    void save(RoomVerificationEntity roomVerificationEntity);

    Optional<RoomVerificationEntity> findById(long roomVerificationId);
}
