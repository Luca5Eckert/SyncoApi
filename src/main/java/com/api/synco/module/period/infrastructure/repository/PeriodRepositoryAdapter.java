package com.api.synco.module.period.infrastructure.repository;

import com.api.synco.module.period.domain.PeriodEntity;
import com.api.synco.module.period.domain.filter.PeriodFilter;
import com.api.synco.module.period.domain.filter.PeriodPage;
import com.api.synco.module.period.domain.port.PeriodRepository;
import com.api.synco.module.period.infrastructure.specification.PeriodFilterProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PeriodRepositoryAdapter implements PeriodRepository {

    private final PeriodRepositoryJpa periodRepositoryJpa;

    private final PeriodFilterProvider periodFilterProvider;

    public PeriodRepositoryAdapter(PeriodRepositoryJpa periodRepositoryJpa, PeriodFilterProvider periodFilterProvider) {
        this.periodRepositoryJpa = periodRepositoryJpa;
        this.periodFilterProvider = periodFilterProvider;
    }

    @Override
    public PeriodEntity save(PeriodEntity period) {
        return periodRepositoryJpa.save(period);
    }

    @Override
    public Optional<PeriodEntity> findById(long periodId) {
        return periodRepositoryJpa.findById(periodId);
    }

    @Override
    public Page<PeriodEntity> findAll(PeriodFilter periodFilter, PeriodPage periodPage) {
        var specification = periodFilterProvider.of(periodFilter);

        PageRequest pageRequest = PageRequest.of(
                periodPage.pageNumber(),
                periodPage.pageSize()
        );

        return periodRepositoryJpa.findAll(specification, pageRequest);
    }

}
