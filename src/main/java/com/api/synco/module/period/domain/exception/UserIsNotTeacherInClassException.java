package com.api.synco.module.period.domain.exception;

import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.class_user.domain.enumerator.TypeUserClass;

public class UserIsNotTeacherInClassException extends PeriodDomainException {

    public UserIsNotTeacherInClassException(long userId, ClassEntityId classId, TypeUserClass actualType) {
        super("User " + userId + " is not a TEACHER in class " + classId + ". Actual type: " + actualType);
    }
}
