package com.api.synco.module.period.domain.use_case;

import com.api.synco.module.period.domain.PeriodEntity;
import com.api.synco.module.period.domain.enumerator.TypePeriod;
import com.api.synco.module.period.domain.filter.PeriodFilter;
import com.api.synco.module.period.domain.filter.PeriodPage;
import com.api.synco.module.period.domain.port.PeriodRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class GetAllPeriodUseCase {

    private final PeriodRepository periodRepository;

    public GetAllPeriodUseCase(PeriodRepository periodRepository) {
        this.periodRepository = periodRepository;
    }

    public Page<PeriodEntity> execute(
            long teacherId,
            long roomId,
            long classId,
            TypePeriod typePeriod,
            int pageNumber,
            int pageSize
    ) {

        PeriodFilter periodFilter = new PeriodFilter(
                teacherId,
                roomId,
                classId,
                typePeriod
        );

        PeriodPage periodPage = new PeriodPage(
                pageNumber,
                pageSize
        );

        return periodRepository.findAll(periodFilter, periodPage);
    }


}
