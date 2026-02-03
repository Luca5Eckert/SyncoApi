package com.api.synco.module.room_verification.domain.port;

import com.api.synco.module.room_verification.domain.RoomVerificationEntity;

public interface RoomVerificationRepository {
    boolean existByPeriodId(long l);

    void save(RoomVerificationEntity roomVerificationEntity);
}
