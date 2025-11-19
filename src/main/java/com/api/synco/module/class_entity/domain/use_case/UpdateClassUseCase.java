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

public class UpdateClassUseCase {

    private final PermissionService permissionService;

    private final ClassRepository classRepository;
    private final UserRepository userRepository;

    public UpdateClassUseCase(PermissionService permissionService, ClassRepository classRepository, UserRepository userRepository) {
        this.permissionService = permissionService;
        this.classRepository = classRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ClassEntity execute(UpdateClassRequest updateClassRequest, long idUser){
        UserEntity userEntity = userRepository.findById(idUser)
                .orElseThrow( () -> new UserNotFoundDomainException(idUser));

        if(!permissionService.canModifyClass(userEntity.getRole())) throw new UserWithoutUpdateClassPermissionException();

        ClassEntityId id = new ClassEntityId(
                updateClassRequest.idCourse(),
                updateClassRequest.number()
        );

        ClassEntity classEntity = classRepository.findById(id)
                .orElseThrow(ClassNotFoundException::new);

        classEntity.update(
                updateClassRequest.totalHours(),
                updateClassRequest.shift()
        );

        classRepository.save(classEntity);

        return classEntity;
    }


}
