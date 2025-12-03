package com.api.synco.module.user.domain.exception.email;

import com.api.synco.module.user.domain.exception.UserDomainException;

/**
 * Exception thrown when an email address is blank or empty.
 *
 * <p>This exception is thrown during email value object construction
 * when the provided email address is blank.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see UserDomainException
 */
public class EmailBlankDomainException extends UserDomainException {

    /**
     * Constructs a new exception with a custom message.
     *
     * @param message the detail message
     */
    public EmailBlankDomainException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the default message.
     */
    public EmailBlankDomainException() {
      super("The email can't be blank");
    }
}
