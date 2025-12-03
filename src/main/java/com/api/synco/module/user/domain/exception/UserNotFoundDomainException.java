package com.api.synco.module.user.domain.exception;

/**
 * Exception thrown when a user cannot be found in the system.
 *
 * <p>This exception is thrown when operations require an existing user
 * but the user cannot be located by their identifier or email.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see UserDomainException
 */
public class UserNotFoundDomainException extends UserDomainException {

    /**
     * Constructs a new exception for a user not found by email.
     *
     * @param email the email address that was searched for
     */
    public UserNotFoundDomainException(String email) {
        super("User not found by email: " + email);
    }

    /**
     * Constructs a new exception for a user not found by ID.
     *
     * @param id the user ID that was searched for
     */
    public UserNotFoundDomainException(long id) {
        super("User not found by id: " + id);
    }

}
