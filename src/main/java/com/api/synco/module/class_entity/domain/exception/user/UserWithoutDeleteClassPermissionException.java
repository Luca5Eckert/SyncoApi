package com.api.synco.module.class_entity.domain.exception.user;

import com.api.synco.module.class_entity.domain.exception.ClassDomainException;

public class UserWithoutDeleteClassPermissionException extends UserWithoutClassPermisionException {
    public UserWithoutDeleteClassPermissionException(String message) {
        super(message);
    }

    public UserWithoutDeleteClassPermissionException() {
        super("The user can't delete the class");
    }
}