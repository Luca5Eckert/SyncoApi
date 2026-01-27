package com.api.synco.module.period.application.dto;

import com.api.synco.module.period.domain.enumerator.TypePeriod;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreatePeriodRequest(
        @NotNull  long teacherId,
        long roomId,
        ClassEntityIdRequest classEntity,
        LocalDate date,
        TypePeriod typePeriod
) {
}
