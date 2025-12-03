package com.api.synco.module.authentication.domain.exception;

/**
 * Base exception class for authentication-related errors.
 *
 * <p>This exception serves as the parent class for all authentication-specific
 * exceptions in the domain layer.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 */
public class AuthenticationException extends RuntimeException {

    /**
     * Constructs a new authentication exception with the specified message.
     *
     * @param message the detail message describing the error
     */
    public AuthenticationException(String message) {
        super(message);
    }
}
