package com.api.synco.module.class_user.application.dto.update;

import com.api.synco.module.class_user.domain.enumerator.TypeUserClass;

public record UpdateClassUserRequest(
        TypeUserClass newTypeUserClass
) {
}
