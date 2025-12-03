package com.api.synco.module.class_user.domain.exception;

public class ClassUserNotFoundException extends ClassUserDomainException {
    public ClassUserNotFoundException(String message) {
        super(message);
    }

    public ClassUserNotFoundException() {
        super("The class user not found by id");
    }
}
