package com.api.synco.module.class_entity.domain.exception;

import com.api.synco.module.class_entity.domain.ClassEntityId;

public class ClassNotFoundException extends ClassDomainException {
    public ClassNotFoundException(String message) {
        super(message);
    }

    public ClassNotFoundException() {
        super("Class not found by id");
    }

}
