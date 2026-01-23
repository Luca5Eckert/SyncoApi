package com.api.synco.module.user.domain.use_case;

import com.api.synco.module.permission.domain.policies.PermissionPolicy;
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

/**
 * Use case for creating new users in the system.
 *
 * <p>This use case handles the business logic for user creation, including:</p>
 * <ul>
 *   <li>Permission verification for the requesting user</li>
 *   <li>Validation of user data (name, email, password)</li>
 *   <li>Password encoding before storage</li>
 *   <li>Email uniqueness verification</li>
 * </ul>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see UserRepository
 * @see PermissionPolicy
 */
@Component
public class UserCreateUseCase {

    private final UserRepository userRepository;

    private final PermissionPolicy permissionPolicy;
    private final PasswordEncoder passwordEncoder;
    private final PasswordValidatorImpl passwordValidator;

    /**
     * Constructs a new user creation use case.
     *
     * @param userRepository the repository for user persistence
     * @param permisionPolicy the service for permission checks
     * @param passwordEncoder the encoder for password hashing
     * @param passwordValidator the validator for password requirements
     */
    public UserCreateUseCase(UserRepository userRepository, PermissionPolicy permisionPolicy, PasswordEncoder passwordEncoder, PasswordValidatorImpl passwordValidator) {
        this.userRepository = userRepository;
        this.permissionPolicy = permisionPolicy;
        this.passwordEncoder = passwordEncoder;
        this.passwordValidator = passwordValidator;
    }

    /**
     * Executes the user creation use case.
     *
     * <p>This method validates the name, email, and password. After validation,
     * the user will be saved in the database.</p>
     *
     * @param userCreateRequest request containing the new user's data
     * @param userId the ID of the authenticated user attempting the creation
     * @return the created user entity
     * @throws UserNotFoundDomainException if the authenticated user is not found
     * @throws UserWithoutCreateUserPermissionException if the user lacks permission
     * @throws PasswordNotValidDomainException if the password doesn't meet requirements
     * @throws EmailNotUniqueDomainException if the email is already in use
     */
    @Transactional
    public UserEntity execute(UserCreateRequest userCreateRequest, long userId) {

        UserEntity userAuthenticated = userRepository.findById(userId)
                .orElseThrow( () -> new UserNotFoundDomainException(userId));

        if(!permissionPolicy.canCreate(userAuthenticated.getRole())){
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
