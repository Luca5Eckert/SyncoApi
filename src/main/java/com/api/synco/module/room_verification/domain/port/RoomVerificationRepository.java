package com.api.synco.module.room_verification.domain.port;

import com.api.synco.module.room_verification.domain.RoomVerificationEntity;

public interface RoomVerificationRepository {
    boolean existsByPeriodId(long periodId);

    void save(RoomVerificationEntity roomVerificationEntity);
}
