package com.api.synco.module.class_user.application.dto.create;

import com.api.synco.module.class_user.application.dto.ClassUserIdResponse;
import com.api.synco.module.class_user.domain.ClassUserId;
import com.api.synco.module.class_user.domain.enumerator.TypeUserClass;

public record CreateClassUserResponse(
        ClassUserIdResponse classUserId,
        TypeUserClass typeUserClass
) {
}
