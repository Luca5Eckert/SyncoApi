package com.api.synco.module.authentication.domain.exception;

/**
 * Exception thrown when authentication validation fails.
 *
 * <p>This exception is thrown when user credentials are invalid or
 * authentication cannot be completed.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see AuthenticationException
 */
public class AuthenticationValidationException extends AuthenticationException {

    /**
     * Constructs a new exception with a custom message.
     *
     * @param message the detail message
     */
    public AuthenticationValidationException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the default message.
     */
    public AuthenticationValidationException(){
        super("Invalid credentials");
    }
}
