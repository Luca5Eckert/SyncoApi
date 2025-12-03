package com.api.synco.module.user.domain.exception.email;

import com.api.synco.module.user.domain.exception.UserDomainException;

/**
 * Exception thrown when attempting to create a user with an email that already exists.
 *
 * <p>This exception is thrown when the email uniqueness constraint would be violated.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see UserDomainException
 */
public class EmailNotUniqueDomainException extends UserDomainException {

    /**
     * Constructs a new exception with a custom message.
     *
     * @param message the detail message
     */
    public EmailNotUniqueDomainException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the default message.
     */
    public EmailNotUniqueDomainException() {
        super("The email is not unique");
    }

}
