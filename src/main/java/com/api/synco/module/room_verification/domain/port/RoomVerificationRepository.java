package com.api.synco.module.room_verification.domain.port;

import com.api.synco.module.room_verification.domain.RoomVerificationEntity;
import com.api.synco.module.room_verification.domain.filter.RoomVerificationFilter;
import com.api.synco.module.room_verification.domain.filter.RoomVerificationPage;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface RoomVerificationRepository {
    boolean existsByPeriodId(long periodId);

    void save(RoomVerificationEntity roomVerificationEntity);

    Optional<RoomVerificationEntity> findById(long roomVerificationId);

    Page<RoomVerificationEntity> findAll(RoomVerificationFilter roomVerificationFilter, RoomVerificationPage roomVerificationPage);
}
