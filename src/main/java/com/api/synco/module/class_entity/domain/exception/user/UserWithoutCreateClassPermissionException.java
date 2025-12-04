package com.api.synco.module.class_entity.domain.exception.user;

import com.api.synco.module.class_entity.domain.exception.ClassDomainException;

public class UserWithoutCreateClassPermissionException extends UserWithoutClassPermisionException {
    public UserWithoutCreateClassPermissionException(String message) {
        super(message);
    }

    public UserWithoutCreateClassPermissionException() {
        super("The user can't create a class");
    }
}
