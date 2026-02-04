package com.api.synco.core.exception.token;

/**
 * Base exception class for JWT token-related errors.
 *
 * <p>This exception serves as the parent class for all token-related exceptions
 * in the application. It is thrown when there are issues with JWT token
 * processing, validation, or parsing.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see TokenInvalidException
 */
public class TokenException extends RuntimeException {

    /**
     * Constructs a new token exception with the specified detail message.
     *
     * @param message the detail message describing the token error
     */
    public TokenException(String message) {
        super(message);
    }
}
