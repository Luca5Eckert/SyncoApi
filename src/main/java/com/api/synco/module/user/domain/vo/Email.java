package com.api.synco.module.user.domain.vo;

import com.api.synco.module.user.domain.exception.email.EmailBlankDomainException;
import com.api.synco.module.user.domain.exception.email.EmailInvalidDomainException;
import com.api.synco.module.user.domain.exception.email.EmailLengthDomainException;
import org.apache.commons.validator.routines.EmailValidator;

/**
 * Value object representing a validated email address.
 *
 * <p>This record encapsulates email validation logic, ensuring that all
 * instances contain valid email addresses. Validation is performed during
 * construction using Apache Commons EmailValidator.</p>
 *
 * <p>Validation rules:</p>
 * <ul>
 *   <li>Email cannot be blank</li>
 *   <li>Email must not exceed 150 characters</li>
 *   <li>Email must follow standard email format</li>
 * </ul>
 *
 * @param address the validated email address string
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @throws EmailBlankDomainException if the email is blank
 * @throws EmailLengthDomainException if the email exceeds 150 characters
 * @throws EmailInvalidDomainException if the email format is invalid
 */
public record Email(String address) {


    /**
     * Constructs a new Email value object with validation.
     *
     * @param address the email address to validate and store
     * @throws EmailBlankDomainException if the email is blank
     * @throws EmailLengthDomainException if the email exceeds 150 characters
     * @throws EmailInvalidDomainException if the email format is invalid
     */
    public Email{
        if(address.isBlank()) throw new EmailBlankDomainException();

        if(address.length() > 150) throw new EmailLengthDomainException(150);

        if(!isValid(address)) throw new EmailInvalidDomainException();

    }

    /**
     * Validates the email format using Apache Commons EmailValidator.
     *
     * @param address the email address to validate
     * @return {@code true} if the email is valid, {@code false} otherwise
     */
    private boolean isValid(String address) {
        return EmailValidator.getInstance().isValid(address);
    }


}
