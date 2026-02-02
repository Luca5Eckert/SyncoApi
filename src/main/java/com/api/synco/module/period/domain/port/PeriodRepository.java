package com.api.synco.module.period.domain.port;

import com.api.synco.module.period.domain.PeriodEntity;

import java.util.Optional;

public interface PeriodRepository {
    PeriodEntity save(PeriodEntity period);

    Optional<PeriodEntity> findById(long periodId);
}
