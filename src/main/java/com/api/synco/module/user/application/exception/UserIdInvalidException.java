package com.api.synco.module.user.application.exception;

/**
 * Exception thrown when a user ID is invalid.
 *
 * <p>This exception is thrown when an operation receives an invalid
 * user identifier, such as a negative ID or an ID that doesn't meet
 * the expected format.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see UserApplicationException
 */
public class UserIdInvalidException extends UserApplicationException {

    /**
     * Constructs a new exception with a custom message.
     *
     * @param message the detail message describing why the ID is invalid
     */
    public UserIdInvalidException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with a default message.
     */
    public UserIdInvalidException() {
        super("User's id is invalid");
    }

}
