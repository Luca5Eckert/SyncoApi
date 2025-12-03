package com.api.synco.module.authentication.domain.exception.password;

import com.api.synco.module.authentication.domain.exception.AuthenticationException;

/**
 * Exception thrown when the provided password doesn't match the stored password.
 *
 * <p>This exception is thrown during password reset when the current password
 * verification fails.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see AuthenticationException
 */
public class PasswordNotMatchesException extends AuthenticationException {

    /**
     * Constructs a new exception with the default message.
     */
    public PasswordNotMatchesException() {
        super("The password is incorrect");
    }
}
