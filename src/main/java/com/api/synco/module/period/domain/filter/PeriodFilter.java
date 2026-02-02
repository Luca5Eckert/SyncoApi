package com.api.synco.module.period.domain.filter;

import com.api.synco.module.period.domain.enumerator.TypePeriod;

public record PeriodFilter(
        long teacherId,
        long roomId,
        long classId,
        TypePeriod typePeriod
) {
}
