package com.api.synco.module.user.domain.exception.permission;

/**
 * Exception thrown when a user lacks permission to create other users.
 *
 * <p>This exception is thrown when a non-administrator user attempts
 * to create a new user account.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 */
public class UserWithoutCreateUserPermissionException extends RuntimeException {

    /**
     * Constructs a new exception with a custom message.
     *
     * @param message the detail message
     */
    public UserWithoutCreateUserPermissionException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the default message.
     */
    public UserWithoutCreateUserPermissionException() {
        super("User don't have the permission to create another user");
    }
}
