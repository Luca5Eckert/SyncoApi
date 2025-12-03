package com.api.synco.module.user.domain.exception;

/**
 * Base exception class for user domain-related errors.
 *
 * <p>This exception serves as the parent class for all user-specific
 * exceptions in the domain layer. It provides a common type for
 * exception handling in the global exception handler.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 */
public class UserDomainException extends RuntimeException {

    /**
     * Constructs a new user domain exception with the specified message.
     *
     * @param message the detail message describing the error
     */
    public UserDomainException(String message) {
        super(message);
    }
}
