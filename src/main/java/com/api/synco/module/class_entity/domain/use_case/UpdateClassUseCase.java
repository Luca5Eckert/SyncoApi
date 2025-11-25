package com.api.synco.module.class_entity.domain.use_case;

import com.api.synco.module.class_entity.application.dto.update.UpdateClassRequest;
import com.api.synco.module.class_entity.domain.ClassEntity;
import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.class_entity.domain.exception.ClassNotFoundException;
import com.api.synco.module.class_entity.domain.exception.user.UserWithoutUpdateClassPermissionException;
import com.api.synco.module.class_entity.domain.port.ClassRepository;
import com.api.synco.module.permission.domain.service.PermissionService;
import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.exception.UserNotFoundDomainException;
import com.api.synco.module.user.domain.port.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class UpdateClassUseCase {

    private final PermissionService permissionService;

    private final ClassRepository classRepository;
    private final UserRepository userRepository;

    public UpdateClassUseCase(PermissionService permissionService, ClassRepository classRepository, UserRepository userRepository) {
        this.permissionService = permissionService;
        this.classRepository = classRepository;
        this.userRepository = userRepository;
    }

    /**
     * Updates the Class entity after validating user permissions and the existence of the associated Class.
     *
     * @param updateClassRequest The DTO (Data Transfer Object) containing the necessary data for class update,
     * such as total hours, and shift.
     * @param classEntityId Class's id who will be updated
     * @param idUser The ID of the authenticated user attempting the update operation.
     *
     * @return The newly persisted {@link ClassEntity} object.
     *
     * @throws UserNotFoundDomainException If the user with the provided {@code idUser} is not found.
     * @throws UserWithoutUpdateClassPermissionException If the authenticated user's role lacks the permission to update classes.
     * @throws ClassNotFoundException If the class referenced by {@code classEntityId} is not found.
     */
    @Transactional
    public ClassEntity execute(UpdateClassRequest updateClassRequest, ClassEntityId classEntityId, long idUser){
        UserEntity userEntity = userRepository.findById(idUser)
                .orElseThrow( () -> new UserNotFoundDomainException(idUser));

        if(!permissionService.canModifyClass(userEntity.getRole()))
            throw new UserWithoutUpdateClassPermissionException();

        ClassEntity classEntity = classRepository.findById(classEntityId)
                .orElseThrow(ClassNotFoundException::new);

        classEntity.update(
                updateClassRequest.totalHours(),
                updateClassRequest.shift()
        );

        classRepository.save(classEntity);

        return classEntity;
    }


}
