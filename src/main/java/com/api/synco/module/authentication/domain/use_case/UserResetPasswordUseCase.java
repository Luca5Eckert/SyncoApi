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

@Component
public class UserResetPasswordUseCase {

    private final UserRepository userRepository;
    private final PasswordValidatorImpl passwordValidator;
    private final PasswordEncoder passwordEncoder;

    public UserResetPasswordUseCase(UserRepository userRepository, PasswordValidatorImpl passwordValidator, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordValidator = passwordValidator;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Resets the user's password
     *
     * <p>First, validates the password's correctness and validity.
     * The password is then encoded, assigned to the user, and saved
     * in the database.</p>
     *
     * @param userResetRequest the request containing the data for the reset
     * @param idUser the user's ID
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
