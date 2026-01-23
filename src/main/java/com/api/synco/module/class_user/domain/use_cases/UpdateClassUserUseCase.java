package com.api.synco.module.class_user.domain.use_cases;

import com.api.synco.module.class_entity.domain.exception.ClassNotFoundException;
import com.api.synco.module.class_user.application.dto.update.UpdateClassUserRequest;
import com.api.synco.module.class_user.domain.ClassUser;
import com.api.synco.module.class_user.domain.ClassUserId;
import com.api.synco.module.class_user.domain.exception.user.UserWithoutCreateClassUserPermissionException;
import com.api.synco.module.class_user.domain.port.ClassUserRepository;
import com.api.synco.module.permission.domain.policies.PermissionPolicy;
import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.exception.UserNotFoundDomainException;
import com.api.synco.module.user.domain.port.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UpdateClassUserUseCase {

    private final ClassUserRepository classUserRepository;
    private final UserRepository userRepository;

    @Qualifier("classUserPermissionPolicy")
    private final PermissionPolicy permissionPolicy;

    public UpdateClassUserUseCase(
            ClassUserRepository classUserRepository,
            UserRepository userRepository,
            @Qualifier("classUserPermissionPolicy") PermissionPolicy permissionPolicy
    ) {
        this.classUserRepository = classUserRepository;
        this.userRepository = userRepository;
        this.permissionPolicy = permissionPolicy;
    }

    @Transactional
    public ClassUser execute(UpdateClassUserRequest updateClassUserRequest, ClassUserId classUserId, long userId){
        UserEntity userAuthenticated = userRepository.findById(userId)
                .orElseThrow( () -> new UserNotFoundDomainException(userId));

        if(!permissionPolicy.canEdit(userAuthenticated.getRole())){
            throw new UserWithoutCreateClassUserPermissionException();
        }

        ClassUser classUser = classUserRepository.findById(classUserId)
                .orElseThrow(ClassNotFoundException::new);

        classUser.update(
                updateClassUserRequest.newTypeUserClass()
        );

        classUserRepository.save(classUser);

        return classUser;
    }

}
