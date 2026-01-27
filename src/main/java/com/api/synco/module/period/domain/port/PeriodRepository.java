package com.api.synco.module.period.domain.port;

import com.api.synco.module.period.domain.PeriodEntity;

public interface PeriodRepository {
    PeriodEntity save(PeriodEntity period);
}
