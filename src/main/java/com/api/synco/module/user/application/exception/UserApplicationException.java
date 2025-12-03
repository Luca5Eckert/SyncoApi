package com.api.synco.module.user.application.exception;

/**
 * Base exception class for user application layer errors.
 *
 * <p>This exception serves as the parent class for all user-related
 * exceptions that occur in the application layer, such as validation
 * and processing errors.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 */
public class UserApplicationException extends RuntimeException {

    /**
     * Constructs a new user application exception with the specified message.
     *
     * @param message the detail message describing the error
     */
    public UserApplicationException(String message) {
        super(message);
    }
}
