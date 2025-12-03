package com.api.synco.module.user.domain.exception.name;

import com.api.synco.module.user.domain.exception.UserDomainException;

/**
 * Exception thrown when a name is blank or empty.
 *
 * <p>This exception is thrown during name value object construction
 * when the provided name is blank.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see UserDomainException
 */
public class NameBlankDomainException extends UserDomainException {

    /**
     * Constructs a new exception with a custom message.
     *
     * @param message the detail message
     */
    public NameBlankDomainException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the default message.
     */
    public NameBlankDomainException() {
      super("The name can't be blank");
    }

}
