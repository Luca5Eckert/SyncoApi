package com.api.synco.module.authentication.domain.use_case;

import com.api.synco.module.authentication.application.dto.register.UserRegisterRequest;
import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.enumerator.RoleUser;
import com.api.synco.module.user.domain.exception.email.EmailNotUniqueDomainException;
import com.api.synco.module.user.domain.exception.password.PasswordNotValidDomainException;
import com.api.synco.module.user.domain.port.UserRepository;
import com.api.synco.module.user.domain.validator.PasswordValidatorImpl;
import com.api.synco.module.user.domain.vo.Email;
import com.api.synco.module.user.domain.vo.Name;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use case for user registration.
 *
 * <p>This use case handles the creation of new user accounts including:</p>
 * <ul>
 *   <li>Validation of user data (name, email, password)</li>
 *   <li>Password strength verification</li>
 *   <li>Email uniqueness check</li>
 *   <li>Password encoding before storage</li>
 * </ul>
 *
 * <p>New users are created with the USER role by default.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see UserRepository
 * @see PasswordValidatorImpl
 */
@Component
public class UserRegisterUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordValidatorImpl passwordValidator;

    /**
     * Constructs a new user registration use case.
     *
     * @param userRepository the repository for user persistence
     * @param passwordEncoder the encoder for password hashing
     * @param passwordValidator the validator for password requirements
     */
    public UserRegisterUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder, PasswordValidatorImpl passwordValidator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordValidator = passwordValidator;
    }

    /**
     * Executes the user registration use case.
     *
     * <p>Validates the name, email, and password. After validation,
     * the user will be saved in the database with the USER role.</p>
     *
     * @param userRegisterRequest request containing the registration data
     * @return the created user entity
     * @throws PasswordNotValidDomainException if the password doesn't meet requirements
     * @throws EmailNotUniqueDomainException if the email is already in use
     */
    @Transactional
    public UserEntity execute(UserRegisterRequest userRegisterRequest) {

        Name name = new Name(userRegisterRequest.name());
        Email email = new Email(userRegisterRequest.email());

        if(!passwordValidator.isValid(userRegisterRequest.password())) throw new PasswordNotValidDomainException();

        String password = passwordEncoder.encode(userRegisterRequest.password());

        UserEntity user = new UserEntity(name, email, password, RoleUser.USER);

        if(userRepository.existsByEmail(user.getEmail())) throw new EmailNotUniqueDomainException();

        userRepository.save(user);

        return user;
    }

}
