package com.api.synco.module.user.domain.vo;

import com.api.synco.module.user.domain.exception.name.NameBlankDomainException;
import com.api.synco.module.user.domain.exception.name.NameLengthDomainException;

/**
 * Value object representing a validated user name.
 *
 * <p>This record encapsulates name validation logic, ensuring that all
 * instances contain valid names. Validation is performed during construction.</p>
 *
 * <p>Validation rules:</p>
 * <ul>
 *   <li>Name cannot be blank</li>
 *   <li>Name must not exceed 30 characters</li>
 * </ul>
 *
 * @param value the validated name string
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @throws NameBlankDomainException if the name is blank
 * @throws NameLengthDomainException if the name exceeds 30 characters
 */
public record Name(String value) {

    /**
     * Constructs a new Name value object with validation.
     *
     * @param value the name to validate and store
     * @throws NameBlankDomainException if the name is blank
     * @throws NameLengthDomainException if the name exceeds 30 characters
     */
    public Name {
        if(value.isBlank()) throw new NameBlankDomainException();

        if(value.length() > 30) throw new NameLengthDomainException(30);

    }
}
