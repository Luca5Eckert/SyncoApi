package com.api.synco.module.class_entity.domain.use_case;

import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.class_entity.domain.exception.ClassNotFoundException;
import com.api.synco.module.class_entity.domain.exception.user.UserWithoutDeleteClassPermissionException;
import com.api.synco.module.class_entity.domain.port.ClassRepository;
import com.api.synco.module.permission.domain.service.PermissionService;
import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.exception.UserNotFoundDomainException;
import com.api.synco.module.user.domain.port.UserRepository;

/**
 * Use case responsible for deleting a class entity.
 *
 * @author lucas_eckert
 * @version 1.0
 */
public class DeleteClassUseCase {

    private final ClassRepository classRepository;
    private final UserRepository userRepository;
    private final PermissionService permissionService;

    public DeleteClassUseCase(ClassRepository classRepository, UserRepository userRepository, PermissionService permissionService) {
        this.classRepository = classRepository;
        this.userRepository = userRepository;
        this.permissionService = permissionService;
    }

    /**
     * Executes the class deletion logic.
     *
     * <p>This method verifies if the user exists, possesses the required permissions,
     * and if the target class exists before performing the deletion.</p>
     *
     * @param idClass The unique identifier of the class to be deleted.
     * @param idUser  The unique identifier of the user requesting the deletion.
     * @throws UserNotFoundDomainException               if the user is not found.
     * @throws UserWithoutDeleteClassPermissionException if the user lacks permission to delete the class.
     * @throws ClassNotFoundException                    if the class does not exist.
     */
    public void execute(ClassEntityId idClass, long idUser) {
        UserEntity userEntity = userRepository.findById(idUser)
                .orElseThrow(() -> new UserNotFoundDomainException(idUser));

        if (!permissionService.canModifyClass(userEntity.getRole())) {
            throw new UserWithoutDeleteClassPermissionException();
        }

        if (!classRepository.existById(idClass)) {
            throw new ClassNotFoundException();
        }

        classRepository.deleteById(idClass);
    }

}