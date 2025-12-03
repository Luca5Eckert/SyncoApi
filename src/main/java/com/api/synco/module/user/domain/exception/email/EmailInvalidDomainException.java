package com.api.synco.module.user.domain.exception.email;

import com.api.synco.module.user.domain.exception.UserDomainException;

/**
 * Exception thrown when an email address format is invalid.
 *
 * <p>This exception is thrown during email value object construction
 * when the provided email address does not conform to standard email format.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see UserDomainException
 */
public class EmailInvalidDomainException extends UserDomainException {

    /**
     * Constructs a new exception with a custom message.
     *
     * @param message the detail message
     */
  public EmailInvalidDomainException(String message) {
    super(message);
  }

    /**
     * Constructs a new exception with the default message.
     */
  public EmailInvalidDomainException() {
    super("The format of email is invalid. Please insert a correct");
  }


}
