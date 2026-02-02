package com.api.synco.module.period.domain.use_case;

import com.api.synco.module.period.domain.PeriodEntity;
import com.api.synco.module.period.domain.exception.PeriodNotFoundException;
import com.api.synco.module.period.domain.port.PeriodRepository;
import org.springframework.stereotype.Component;

@Component
public class GetPeriodUseCase {

    private final PeriodRepository periodRepository;

    public GetPeriodUseCase(PeriodRepository periodRepository) {
        this.periodRepository = periodRepository;
    }

    public PeriodEntity execute(long periodId){
        return periodRepository.findById(periodId)
                .orElseThrow(PeriodNotFoundException::new);
    }


}
