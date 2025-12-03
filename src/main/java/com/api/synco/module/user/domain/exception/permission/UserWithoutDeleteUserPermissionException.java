package com.api.synco.module.user.domain.exception.permission;

import com.api.synco.module.user.domain.exception.UserDomainException;

/**
 * Exception thrown when a user lacks permission to delete other users.
 *
 * <p>This exception is thrown when a non-administrator user attempts
 * to delete a user account.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see UserDomainException
 */
public class UserWithoutDeleteUserPermissionException extends UserDomainException {

    /**
     * Constructs a new exception with a custom message.
     *
     * @param message the detail message
     */
    public UserWithoutDeleteUserPermissionException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the default message.
     */
    public UserWithoutDeleteUserPermissionException() {
        super("User don't have the permission to delete another user");
    }

}
