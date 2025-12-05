package com.api.synco.module.class_user.application.dto;

import com.api.synco.module.class_entity.application.dto.ClassIdResponse;
import com.api.synco.module.class_entity.domain.ClassEntityId;

public record ClassUserIdResponse(
        ClassIdResponse classIdResponse,
        long idCourse
) {
}
