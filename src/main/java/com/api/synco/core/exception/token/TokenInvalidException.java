package com.api.synco.core.exception.token;

/**
 * Exception thrown when a JWT token is invalid.
 *
 * <p>This exception is thrown in various scenarios including:</p>
 * <ul>
 *   <li>Token is null or empty</li>
 *   <li>Token has expired</li>
 *   <li>Token is malformed</li>
 *   <li>Token signature is invalid</li>
 *   <li>Token format is unsupported</li>
 * </ul>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see TokenException
 */
public class TokenInvalidException extends TokenException {

    /**
     * Constructs a new token invalid exception with a default message.
     */
    public TokenInvalidException() {
        super("Token is invalid");
    }

    /**
     * Constructs a new token invalid exception with a specific message.
     *
     * @param message the detail message describing why the token is invalid
     */
    public TokenInvalidException(String message) {
        super(message);
    }
}
