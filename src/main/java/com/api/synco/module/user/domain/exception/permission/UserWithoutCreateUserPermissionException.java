package com.api.synco.module.user.domain.exception.permission;

public class UserWithoutCreateUserPermissionException extends RuntimeException {
    public UserWithoutCreateUserPermissionException(String message) {
        super(message);
    }

    public UserWithoutCreateUserPermissionException() {
        super("User don't have the permission to create another user");
    }
}
