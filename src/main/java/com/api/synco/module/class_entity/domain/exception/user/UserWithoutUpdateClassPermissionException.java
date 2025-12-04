package com.api.synco.module.class_entity.domain.exception.user;

import com.api.synco.module.class_entity.domain.exception.ClassDomainException;

public class UserWithoutUpdateClassPermissionException extends UserWithoutClassPermisionException {
    public UserWithoutUpdateClassPermissionException(String message) {
        super(message);
    }

    public UserWithoutUpdateClassPermissionException() {
        super("The user can't update the class");
    }
}
