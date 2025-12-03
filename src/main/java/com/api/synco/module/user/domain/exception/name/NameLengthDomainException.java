package com.api.synco.module.user.domain.exception.name;

import com.api.synco.module.user.domain.exception.UserDomainException;

/**
 * Exception thrown when a name exceeds the maximum allowed length.
 *
 * <p>This exception is thrown during name value object construction
 * when the provided name is too long.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see UserDomainException
 */
public class NameLengthDomainException extends UserDomainException {

    /**
     * Constructs a new exception with a custom message.
     *
     * @param message the detail message
     */
    public NameLengthDomainException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception indicating the maximum allowed length.
     *
     * @param length the maximum allowed length
     */
    public NameLengthDomainException(int length){
      super("The name length is invalid. The maximum caracters is " + length);
    }

}
