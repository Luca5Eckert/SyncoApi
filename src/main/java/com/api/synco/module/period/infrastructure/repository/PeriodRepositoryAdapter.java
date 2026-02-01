package com.api.synco.module.period.infrastructure.repository;

import com.api.synco.module.period.domain.PeriodEntity;
import com.api.synco.module.period.domain.port.PeriodRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PeriodRepositoryAdapter implements PeriodRepository {

    private final PeriodRepositoryJpa periodRepositoryJpa;

    public PeriodRepositoryAdapter(PeriodRepositoryJpa periodRepositoryJpa) {
        this.periodRepositoryJpa = periodRepositoryJpa;
    }

    @Override
    public PeriodEntity save(PeriodEntity period) {
        return periodRepositoryJpa.save(period);
    }
}
