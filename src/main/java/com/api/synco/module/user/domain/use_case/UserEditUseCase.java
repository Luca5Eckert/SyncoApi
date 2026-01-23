package com.api.synco.module.user.domain.use_case;

import com.api.synco.module.permission.domain.policies.PermissionPolicy;
import com.api.synco.module.user.application.dto.edit.UserEditRequest;
import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.exception.UserNotFoundDomainException;
import com.api.synco.module.user.domain.exception.permission.UserWithoutEditUserPermissionException;
import com.api.synco.module.user.domain.port.UserRepository;
import com.api.synco.module.user.domain.vo.Email;
import com.api.synco.module.user.domain.vo.Name;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use case for editing existing users in the system.
 *
 * <p>This use case handles the business logic for user profile editing, including:</p>
 * <ul>
 *   <li>Permission verification (admin can edit anyone, users can edit themselves)</li>
 *   <li>Validation of updated user data</li>
 *   <li>Persistence of changes</li>
 * </ul>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see UserRepository
 * @see PermissionPolicy
 */
@Component
public class UserEditUseCase {

    private final PermissionPolicy permissionPolicy;

    private final UserRepository userRepository;

    /**
     * Constructs a new user edit use case.
     *
     * @param permissionPolicy the service for permission checks
     * @param userRepository the repository for user persistence
     */
    public UserEditUseCase(PermissionPolicy permissionPolicy, UserRepository userRepository) {
        this.permissionPolicy = permissionPolicy;
        this.userRepository = userRepository;
    }

    /**
     * Executes the user edit use case.
     *
     * <p>Validates that the authenticated user has permission to edit the target
     * user. Administrators can edit any user, while regular users can only edit
     * their own profile.</p>
     *
     * @param userEditRequest the request containing updated user data
     * @param idUserAutenticated the ID of the authenticated user performing the edit
     * @return the updated user entity
     * @throws UserNotFoundDomainException if either user is not found
     * @throws UserWithoutEditUserPermissionException if the user lacks permission
     */
    @Transactional
    public UserEntity execute(UserEditRequest userEditRequest, long idUserAutenticated) {
        UserEntity userAuthenticated = userRepository.findById(idUserAutenticated).orElseThrow(() -> new UserNotFoundDomainException(idUserAutenticated));
        UserEntity userEdit = userRepository.findById(userEditRequest.id()).orElseThrow( () -> new UserNotFoundDomainException(userEditRequest.id()));

        if(!canEditUser(userAuthenticated, userEdit) ) throw new UserWithoutEditUserPermissionException();

        editUser(userEdit, userEditRequest);

        userRepository.save(userEdit);

        return userEdit;

    }

    /**
     * Updates the user entity with the new values.
     *
     * @param userEdit the user entity to update
     * @param userEditRequest the request containing the new values
     */
    private void editUser(UserEntity userEdit, UserEditRequest userEditRequest) {
        Name name = new Name(userEditRequest.name());
        Email email = new Email(userEditRequest.email());

        userEdit.setName(name);
        userEdit.setEmail(email);
    }

    /**
     * Checks if the authenticated user can edit the target user.
     *
     * @param userAutenticated the authenticated user
     * @param userEdit the user to be edited
     * @return {@code true} if the edit is permitted, {@code false} otherwise
     */
    private boolean canEditUser(UserEntity userAutenticated, UserEntity userEdit) {
        if(permissionPolicy.canEdit(userAutenticated.getRole())) return true;

        return userEdit.equals(userAutenticated);
    }

}
