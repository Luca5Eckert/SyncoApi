package com.api.synco.module.user.domain.validator;

import org.passay.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Password validator implementation using the Passay library.
 *
 * <p>This component validates passwords against a set of security rules
 * to ensure strong password requirements are met.</p>
 *
 * <p>Validation rules:</p>
 * <ul>
 *   <li>Length between 8 and 30 characters</li>
 *   <li>At least one uppercase letter</li>
 *   <li>At least one lowercase letter</li>
 *   <li>At least one digit</li>
 *   <li>At least one special character</li>
 *   <li>No whitespace allowed</li>
 * </ul>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class PasswordValidatorImpl{

    private final PasswordValidator validator;

    /**
     * Constructs a new password validator with default security rules.
     */
    public PasswordValidatorImpl() {
        List<Rule> rules = Arrays.asList(
                new LengthRule(8, 30),

                new CharacterRule(EnglishCharacterData.UpperCase, 1),

                new CharacterRule(EnglishCharacterData.LowerCase, 1),

                new CharacterRule(EnglishCharacterData.Digit, 1),

                new CharacterRule(EnglishCharacterData.Special, 1),

                new WhitespaceRule()
        );

        this.validator = new PasswordValidator(rules);
    }

    /**
     * Validates a password against the configured security rules.
     *
     * @param password the password to validate
     * @return {@code true} if the password meets all requirements, {@code false} otherwise
     */
    public boolean isValid(String password){
        if (password == null) {
            return false;
        }
        return validator.validate(new PasswordData(password)).isValid();
    }

}
