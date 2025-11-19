package com.api.synco.module.class_entity.application.dto.update;

import com.api.synco.module.class_entity.application.dto.ClassIdResponse;
import com.api.synco.module.class_entity.domain.enumerator.Shift;

public record UpdateClassResponse(
        ClassIdResponse classIdResponse,
        int totalHours,
        Shift shift
) {
}
