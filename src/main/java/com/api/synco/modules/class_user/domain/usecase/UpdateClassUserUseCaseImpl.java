package com.api.synco.modules.class_user.domain.usecase;

import com.api.synco.module.class_entity.domain.exception.ClassNotFoundException;
import com.api.synco.module.class_user.domain.ClassUser;
import com.api.synco.module.class_user.domain.ClassUserId;
import com.api.synco.module.class_user.domain.exception.user.UserWithoutCreateClassUserPermissionException;
import com.api.synco.module.permission.domain.service.PermissionService;
import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.exception.UserNotFoundDomainException;
import com.api.synco.module.user.domain.port.UserRepository;
import com.api.synco.modules.class_user.domain.command.UpdateClassUserCommand;
import com.api.synco.modules.class_user.domain.port.ClassUserRepositoryPort;

/**
 * Use case for updating a class-user association.
 *
 * <p>This use case handles the business logic for updating class-user associations.
 * It accepts pure Java Command records instead of Web DTOs, maintaining domain purity.
 * The class contains no Spring annotations - dependency injection is handled by
 * constructor injection with framework configuration in the infrastructure layer.</p>
 *
 * <p>Business rules enforced:</p>
 * <ul>
 *   <li>The authenticated user must exist</li>
 *   <li>The authenticated user must have permission to modify class-user associations</li>
 *   <li>The target class-user association must exist</li>
 * </ul>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see UpdateClassUserCommand
 * @see ClassUserRepositoryPort
 */
public class UpdateClassUserUseCaseImpl {

    private final ClassUserRepositoryPort classUserRepository;
    private final UserRepository userRepository;
    private final PermissionService permissionService;

    /**
     * Constructs a new UpdateClassUserUseCase.
     *
     * @param classUserRepository the repository port for class-user operations
     * @param userRepository the repository port for user operations
     * @param permissionService the service for permission checks
     */
    public UpdateClassUserUseCaseImpl(
            ClassUserRepositoryPort classUserRepository,
            UserRepository userRepository,
            PermissionService permissionService
    ) {
        this.classUserRepository = classUserRepository;
        this.userRepository = userRepository;
        this.permissionService = permissionService;
    }

    /**
     * Executes the update class-user use case.
     *
     * <p>This method validates the authenticated user's permissions,
     * retrieves the target class-user association, and applies the update.</p>
     *
     * @param command the command containing the update data
     * @param classUserId the composite identifier of the class-user to update
     * @param authenticatedUserId the ID of the user performing the operation
     * @return the updated class-user entity
     * @throws UserNotFoundDomainException if the authenticated user does not exist
     * @throws UserWithoutCreateClassUserPermissionException if the user lacks permission
     * @throws ClassNotFoundException if the class-user association does not exist
     */
    public ClassUser execute(UpdateClassUserCommand command, ClassUserId classUserId, long authenticatedUserId) {
        // Validate authenticated user exists
        UserEntity userAuthenticated = userRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new UserNotFoundDomainException(authenticatedUserId));

        // Validate permission
        if (!permissionService.canModifyClassUser(userAuthenticated.getRole())) {
            throw new UserWithoutCreateClassUserPermissionException();
        }

        // Retrieve and validate target class-user
        ClassUser classUser = classUserRepository.findById(classUserId)
                .orElseThrow(ClassNotFoundException::new);

        // Apply update
        classUser.update(command.newTypeUserClass());

        // Persist changes
        classUserRepository.save(classUser);

        return classUser;
    }
}
