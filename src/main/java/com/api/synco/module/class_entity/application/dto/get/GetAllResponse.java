package com.api.synco.module.class_entity.application.dto.get;

import com.api.synco.module.class_entity.application.dto.ClassIdResponse;
import com.api.synco.module.class_entity.domain.enumerator.Shift;

public record GetAllResponse (
        ClassIdResponse classIdResponse,
        int totalHours,
        Shift shift
) {
}

