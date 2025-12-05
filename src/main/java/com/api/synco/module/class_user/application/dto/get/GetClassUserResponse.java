package com.api.synco.module.class_user.application.dto.get;

import com.api.synco.module.class_user.application.dto.ClassUserIdResponse;
import com.api.synco.module.class_user.domain.enumerator.TypeUserClass;

public record GetClassUserResponse(
        ClassUserIdResponse classUserIdResponse,
        TypeUserClass typeUserClass
) {
}
