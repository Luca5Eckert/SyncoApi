package com.api.synco.module.user.domain.use_case;

import com.api.synco.module.permission.domain.service.PermissionService;
import com.api.synco.module.user.application.dto.create.UserCreateRequest;
import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.exception.UserNotFoundDomainException;
import com.api.synco.module.user.domain.exception.email.EmailNotUniqueDomainException;
import com.api.synco.module.user.domain.exception.password.PasswordNotValidDomainException;
import com.api.synco.module.user.domain.exception.permission.UserWithoutCreateUserPermissionException;
import com.api.synco.module.user.domain.port.UserRepository;
import com.api.synco.module.user.domain.validator.PasswordValidatorImpl;
import com.api.synco.module.user.domain.vo.Email;
import com.api.synco.module.user.domain.vo.Name;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserCreateUseCase {

    private final UserRepository userRepository;

    private final PermissionService permissionService;
    private final PasswordEncoder passwordEncoder;
    private final PasswordValidatorImpl passwordValidator;

    public UserCreateUseCase(UserRepository userRepository, PermissionService permissionService, PasswordEncoder passwordEncoder, PasswordValidatorImpl passwordValidator) {
        this.userRepository = userRepository;
        this.permissionService = permissionService;
        this.passwordEncoder = passwordEncoder;
        this.passwordValidator = passwordValidator;
    }

    /**
     * Method responsible for executing the use case.
     *
     * <p>This method validates the name, email, and password. After validation,
     * the user will be saved in the database.</p>
     *
     * @param userCreateRequest Request with user data.
     * @param userId The ID of the authenticated user attempting the creation operation.
     * @return The created user.
     */
    @Transactional
    public UserEntity execute(UserCreateRequest userCreateRequest, long userId) {

        UserEntity userAuthenticated = userRepository.findById(userId)
                .orElseThrow( () -> new UserNotFoundDomainException(userId));

        if(!permissionService.canModifyUser(userAuthenticated.getRole())){
            throw new UserWithoutCreateUserPermissionException();
        }

        Name name = new Name(userCreateRequest.name());
        Email email = new Email(userCreateRequest.email());

        if(!passwordValidator.isValid(userCreateRequest.password())) throw new PasswordNotValidDomainException();

        String password = passwordEncoder.encode(userCreateRequest.password());

        UserEntity user = new UserEntity(name, email, password, userCreateRequest.roleUser());

        if(userRepository.existsByEmail(user.getEmail())) throw new EmailNotUniqueDomainException();

        userRepository.save(user);

        return user;
    }


}
