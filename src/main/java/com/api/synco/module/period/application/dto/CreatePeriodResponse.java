package com.api.synco.module.period.application.dto;

import com.api.synco.module.period.domain.enumerator.TypePeriod;

public record CreatePeriodResponse(
        long id,
        long teacherId,
        long roomId,
        ClassEntityIdRequest classId,
        String date,
        TypePeriod typePeriod
) {
}
