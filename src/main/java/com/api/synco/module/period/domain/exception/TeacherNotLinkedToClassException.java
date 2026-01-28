package com.api.synco.module.period.domain.exception;

import com.api.synco.module.class_entity.domain.ClassEntityId;

public class TeacherNotLinkedToClassException extends PeriodDomainException {
    public TeacherNotLinkedToClassException(long teacherId, ClassEntityId classId) {
        super("Teacher " + teacherId + " is not linked to class " + classId);
    }
}
