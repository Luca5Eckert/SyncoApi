package com.api.synco.module.user.domain.exception.email;

import com.api.synco.module.user.domain.exception.UserDomainException;

/**
 * Exception thrown when an email address exceeds the maximum allowed length.
 *
 * <p>This exception is thrown during email value object construction
 * when the provided email address is too long.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see UserDomainException
 */
public class EmailLengthDomainException extends UserDomainException {

    /**
     * Constructs a new exception with a custom message.
     *
     * @param message the detail message
     */
    public EmailLengthDomainException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception indicating the maximum allowed length.
     *
     * @param length the maximum allowed length
     */
    public EmailLengthDomainException(int length) {
        super("The email length is invalid. The maximum caracters is " + length);
    }

}
