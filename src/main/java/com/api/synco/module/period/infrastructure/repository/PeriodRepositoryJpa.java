package com.api.synco.module.period.infrastructure.repository;

import com.api.synco.module.period.domain.PeriodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodRepositoryJpa extends JpaRepository<PeriodEntity, Long>, JpaSpecificationExecutor<PeriodEntity> {
}
