package com.api.synco.module.room_verification.domain.port;

import com.api.synco.module.room_verification.domain.RoomVerificationEntity;
import com.api.synco.module.room_verification.domain.permission.RoomVerificationPermissionPolicy;

public interface RoomVerificationRepository {
    boolean existByPeriodId(long l);

    RoomVerificationEntity save(RoomVerificationEntity roomVerificationEntity);
}
