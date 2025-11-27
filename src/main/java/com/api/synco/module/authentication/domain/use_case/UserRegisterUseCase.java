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

@Component
public class UserRegisterUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordValidatorImpl passwordValidator;

    public UserRegisterUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder, PasswordValidatorImpl passwordValidator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordValidator = passwordValidator;
    }

    /**
     * Method responsible for executing the use case.
     *
     * <p>This method will validate the name, email, and password. After validation,
     * the user will be saved in the database.</p>
     *
     * @param userRegisterRequest Request with user data.
     * @return The created user.
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
