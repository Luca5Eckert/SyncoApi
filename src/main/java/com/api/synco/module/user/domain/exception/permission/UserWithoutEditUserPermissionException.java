package com.api.synco.module.user.domain.exception.permission;

import com.api.synco.module.user.domain.exception.UserDomainException;

public class UserWithoutEditUserPermissionException extends UserDomainException {
    public UserWithoutEditUserPermissionException(String message) {
        super(message);
    }

    public UserWithoutEditUserPermissionException() {
      super("User don't have the permission to edit another user");
    }
}
