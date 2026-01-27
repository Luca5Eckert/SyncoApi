package com.api.synco.module.period.domain.command;

import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.period.application.dto.ClassEntityIdRequest;
import com.api.synco.module.period.domain.enumerator.TypePeriod;

import java.time.LocalDate;

public record CreatePeriodCommand(
        long authenticatedUserId,
        long teacherId,
        long roomId,
        ClassEntityId classId,
        LocalDate date,
        TypePeriod typePeriod
) {
}
