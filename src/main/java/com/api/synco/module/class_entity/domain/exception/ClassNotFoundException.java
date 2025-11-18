package com.api.synco.module.class_entity.domain.exception;

public class ClassNotFoundException extends ClassDomainException {
    public ClassNotFoundException(String message) {
        super(message);
    }

    public ClassNotFoundException(long id) {
        super("Class not found by id: " + id);
    }

}
