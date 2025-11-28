package com.api.synco.module.user.domain.exception.permission;

import com.api.synco.module.user.domain.exception.UserDomainException;

public class UserWithoutDeleteUserPermissionException extends UserDomainException {
    public UserWithoutDeleteUserPermissionException(String message) {
        super(message);
    }

    public UserWithoutDeleteUserPermissionException() {
        super("User don't have the permission to delete another user");
    }

}
