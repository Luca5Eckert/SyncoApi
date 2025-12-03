package com.api.synco.module.class_user.application.dto.create;

import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.class_user.domain.enumerator.TypeUserClass;

public record CreateClassUserRequest(
        long userId,
        long idCourse,
        int numberClass,
        TypeUserClass typeUserClass
) {
}
