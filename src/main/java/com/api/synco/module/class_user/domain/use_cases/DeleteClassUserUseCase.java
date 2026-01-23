package com.api.synco.module.class_user.domain.use_cases;

import com.api.synco.module.class_user.domain.ClassUserId;
import com.api.synco.module.class_user.domain.exception.ClassUserNotFoundException;
import com.api.synco.module.class_user.domain.exception.user.UserWithoutCreateClassUserPermissionException;
import com.api.synco.module.class_user.domain.port.ClassUserRepository;
import com.api.synco.module.permission.domain.policies.PermissionPolicy;
import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.exception.UserNotFoundDomainException;
import com.api.synco.module.user.domain.port.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeleteClassUserUseCase {

    private final ClassUserRepository classUserRepository;
    private final UserRepository userRepository;
    private final PermissionPolicy permissionPolicy;

    public DeleteClassUserUseCase(ClassUserRepository classUserRepository,
                                  UserRepository userRepository,
                                  PermissionPolicy permissionPolicy) {
        this.classUserRepository = classUserRepository;
        this.userRepository = userRepository;
        this.permissionPolicy = permissionPolicy;
    }

    @Transactional
    public void execute(ClassUserId classUserId, long idUser) {
        UserEntity userAuthenticated = userRepository.findById(idUser)
                .orElseThrow(() -> new UserNotFoundDomainException(idUser));

        if (!permissionPolicy.canDelete(userAuthenticated.getRole())) {
            throw new UserWithoutCreateClassUserPermissionException();
        }

        if (!classUserRepository.existById(classUserId)) {
            throw new ClassUserNotFoundException();
        }

        classUserRepository.deleteById(classUserId);
    }

}