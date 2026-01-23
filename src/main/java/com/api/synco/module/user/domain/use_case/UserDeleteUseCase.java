package com.api.synco.module.user.domain.use_case;

import com.api.synco.module.permission.domain.policies.PermissionPolicy;
import com.api.synco.module.user.application.dto.delete.UserDeleteRequest;
import com.api.synco.module.user.domain.exception.UserNotFoundDomainException;
import com.api.synco.module.user.domain.exception.permission.UserWithoutDeleteUserPermissionException;
import com.api.synco.module.user.domain.port.UserRepository;
import com.api.synco.module.user.domain.validator.PasswordValidatorImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use case for deleting users from the system.
 *
 * <p>This use case handles the business logic for user deletion, including:</p>
 * <ul>
 *   <li>Verification that the user to delete exists</li>
 *   <li>Permission verification for the requesting user</li>
 *   <li>Actual deletion from the database</li>
 * </ul>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see UserRepository
 * @see PermissionPolicy
 */
@Component
public class UserDeleteUseCase {

    private final PermissionPolicy permissionPolicy;

    private final UserRepository userRepository;

    /**
     * Constructs a new user deletion use case.
     *
     * @param permissionPolicy the service for permission checks
     * @param userRepository the repository for user persistence
     */
    public UserDeleteUseCase(
            UserRepository userRepository,
            @Qualifier("userPermissionPolicy") PermissionPolicy permissionPolicy
    ) {
        this.permissionPolicy = permissionPolicy;
        this.userRepository = userRepository;
    }

    /**
     * Executes the user deletion use case.
     *
     * <p>Validates that the target user exists and that the authenticated
     * user has permission to perform the deletion.</p>
     *
     * @param userDeleteRequest the request containing the user ID to delete
     * @param idUserAutenticated the ID of the authenticated user performing the deletion
     * @throws UserNotFoundDomainException if either user is not found
     * @throws UserWithoutDeleteUserPermissionException if the user lacks permission
     */
    @Transactional
    public void execute(@Valid UserDeleteRequest userDeleteRequest, long idUserAutenticated) {

        if(!userRepository.existsById(userDeleteRequest.id())) throw new UserNotFoundDomainException(userDeleteRequest.id());

        var userAuthenticated = userRepository.findById(idUserAutenticated).orElseThrow(() -> new UserNotFoundDomainException(idUserAutenticated));

        if(!permissionPolicy.canDelete(userAuthenticated.getRole())) {
            throw new UserWithoutDeleteUserPermissionException();
        }

        userRepository.deleteById(userDeleteRequest.id());

    }

}
