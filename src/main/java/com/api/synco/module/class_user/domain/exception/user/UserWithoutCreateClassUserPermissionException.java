package com.api.synco.module.class_user.domain.exception.user;

import com.api.synco.module.class_user.domain.exception.ClassUserDomainException;

public class UserWithoutCreateClassUserPermissionException extends ClassUserDomainException {
    public UserWithoutCreateClassUserPermissionException(String message) {
        super(message);
    }

    public UserWithoutCreateClassUserPermissionException() {
        super("User not have permission to create class user");
    }
}
