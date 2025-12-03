package com.api.synco.module.user.domain.exception.permission;

import com.api.synco.module.user.domain.exception.UserDomainException;

/**
 * Exception thrown when a user lacks permission to edit other users.
 *
 * <p>This exception is thrown when a non-administrator user attempts
 * to edit another user's account.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see UserDomainException
 */
public class UserWithoutEditUserPermissionException extends UserDomainException {

    /**
     * Constructs a new exception with a custom message.
     *
     * @param message the detail message
     */
    public UserWithoutEditUserPermissionException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the default message.
     */
    public UserWithoutEditUserPermissionException() {
      super("User don't have the permission to edit another user");
    }
}
