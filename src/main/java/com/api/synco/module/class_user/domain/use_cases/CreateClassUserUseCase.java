package com.api.synco.module.class_user.domain.use_cases;

import com.api.synco.module.class_entity.domain.ClassEntity;
import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.class_entity.domain.exception.ClassNotFoundException;
import com.api.synco.module.class_entity.domain.port.ClassRepository;
import com.api.synco.module.class_user.application.dto.create.CreateClassUserRequest;
import com.api.synco.module.class_user.domain.ClassUser;
import com.api.synco.module.class_user.domain.ClassUserId;
import com.api.synco.module.class_user.domain.exception.user.UserWithoutCreateClassUserPermissionException;
import com.api.synco.module.class_user.domain.port.ClassUserRepository;
import com.api.synco.module.permission.domain.policies.PermissionPolicy;
import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.exception.UserNotFoundDomainException;
import com.api.synco.module.user.domain.port.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreateClassUserUseCase {

    private final ClassUserRepository classUserRepository;

    private final ClassRepository classRepository;
    private final UserRepository userRepository;

    private final PermissionPolicy permissionPolicy;

    public CreateClassUserUseCase(ClassUserRepository classUserRepository, ClassRepository classRepository, UserRepository userRepository, PermissionPolicy permissionPolicy) {
        this.classUserRepository = classUserRepository;
        this.classRepository = classRepository;
        this.userRepository = userRepository;
        this.permissionPolicy = permissionPolicy;
    }

    @Transactional
    public ClassUser execute(CreateClassUserRequest createClassUserRequest, long idUser){
        UserEntity userAuthenticated = userRepository.findById(idUser)
                .orElseThrow( () -> new UserNotFoundDomainException(idUser));

        if(!permissionPolicy.canCreate(userAuthenticated.getRole())){
            throw new UserWithoutCreateClassUserPermissionException();
        }

        UserEntity user = userRepository.findById(createClassUserRequest.userId())
                .orElseThrow( () -> new UserNotFoundDomainException(createClassUserRequest.userId()));

        ClassEntityId classEntityId = new ClassEntityId(createClassUserRequest.idCourse(), createClassUserRequest.numberClass());
        ClassEntity classEntity = classRepository.findById(classEntityId)
                .orElseThrow(ClassNotFoundException::new);

        ClassUserId classUserId = new ClassUserId(
                createClassUserRequest.userId(),
                classEntityId
        );

        ClassUser classUser = new ClassUser(
                classUserId,
                classEntity,
                user,
                createClassUserRequest.typeUserClass()
        );

        classUserRepository.save(classUser);

        return classUser;
    }


}
