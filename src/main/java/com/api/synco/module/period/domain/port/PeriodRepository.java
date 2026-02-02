package com.api.synco.module.period.domain.port;

import com.api.synco.module.period.domain.PeriodEntity;
import com.api.synco.module.period.domain.filter.PeriodFilter;
import com.api.synco.module.period.domain.filter.PeriodPage;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface PeriodRepository {
    PeriodEntity save(PeriodEntity period);

    Optional<PeriodEntity> findById(long periodId);

    Page<PeriodEntity> findAll(PeriodFilter periodFilter, PeriodPage periodPage);

}
