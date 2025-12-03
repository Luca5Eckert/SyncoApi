package com.api.synco.module.user.domain.exception.password;

import com.api.synco.module.user.domain.exception.UserDomainException;

/**
 * Exception thrown when a password does not meet validation requirements.
 *
 * <p>This exception is thrown when the provided password fails to meet
 * the security requirements defined by the password validator.</p>
 *
 * <p>Password requirements typically include:</p>
 * <ul>
 *   <li>Minimum length of 8 characters</li>
 *   <li>At least one uppercase letter</li>
 *   <li>At least one lowercase letter</li>
 *   <li>At least one digit</li>
 *   <li>At least one special character</li>
 *   <li>No whitespace</li>
 * </ul>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see UserDomainException
 */
public class PasswordNotValidDomainException extends UserDomainException {

    /**
     * Constructs a new exception with a custom message.
     *
     * @param message the detail message
     */
    public PasswordNotValidDomainException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the default message.
     */
    public PasswordNotValidDomainException() {
        super("The password is not valid");
    }
}
