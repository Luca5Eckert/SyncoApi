package com.api.synco.module.authentication.domain.use_case;

import com.api.synco.module.authentication.application.dto.reset_password.UserResetRequest;
import com.api.synco.module.authentication.domain.exception.password.PasswordNotMatchesException;
import com.api.synco.module.user.domain.exception.UserNotFoundDomainException;
import com.api.synco.module.user.domain.exception.password.PasswordNotValidDomainException;
import com.api.synco.module.user.domain.port.UserRepository;
import com.api.synco.module.user.domain.validator.PasswordValidatorImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use case for resetting user passwords.
 *
 * <p>This use case handles password reset for authenticated users including:</p>
 * <ul>
 *   <li>Current password verification</li>
 *   <li>New password validation</li>
 *   <li>Password encoding and persistence</li>
 * </ul>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see UserRepository
 * @see PasswordValidatorImpl
 */
@Component
public class UserResetPasswordUseCase {

    private final UserRepository userRepository;
    private final PasswordValidatorImpl passwordValidator;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs a new password reset use case.
     *
     * @param userRepository the repository for user persistence
     * @param passwordValidator the validator for password requirements
     * @param passwordEncoder the encoder for password hashing
     */
    public UserResetPasswordUseCase(UserRepository userRepository, PasswordValidatorImpl passwordValidator, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordValidator = passwordValidator;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Executes the password reset use case.
     *
     * <p>Validates the current password, verifies the new password meets
     * requirements, encodes it, and saves the updated user.</p>
     *
     * @param userResetRequest the request containing current and new passwords
     * @param idUser the ID of the user requesting the password reset
     * @throws UserNotFoundDomainException if the user is not found
     * @throws PasswordNotMatchesException if the current password is incorrect
     * @throws PasswordNotValidDomainException if the new password doesn't meet requirements
     */
    @Transactional
    public void execute(UserResetRequest userResetRequest, long idUser) {
        var userDetails = userRepository.findById(idUser).orElseThrow( () -> new UserNotFoundDomainException(idUser) );

        if(!passwordEncoder.matches(userResetRequest.passwordActual(), userDetails.getPassword())){
           throw new PasswordNotMatchesException();
        }

        if(!passwordValidator.isValid(userResetRequest.newPassword())) throw new PasswordNotValidDomainException();

        String passwordEncoded = passwordEncoder.encode(userResetRequest.newPassword());

        userDetails.setPassword(passwordEncoded);

        userRepository.save(userDetails);
    }

}
