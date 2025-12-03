package com.api.synco.module.class_user.domain.use_cases;

import com.api.synco.module.class_user.domain.ClassUserId;
import com.api.synco.module.class_user.domain.exception.ClassUserNotFoundException;
import com.api.synco.module.class_user.domain.exception.user.UserWithoutCreateClassUserPermissionException;
import com.api.synco.module.class_user.domain.port.ClassUserRepository;
import com.api.synco.module.permission.domain.service.PermissionService;
import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.exception.UserNotFoundDomainException;
import com.api.synco.module.user.domain.port.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeleteClassUserUseCase {

    private final ClassUserRepository classUserRepository;
    private final UserRepository userRepository;
    private final PermissionService permissionService;

    public DeleteClassUserUseCase(ClassUserRepository classUserRepository,
                                  UserRepository userRepository,
                                  PermissionService permissionService) {
        this.classUserRepository = classUserRepository;
        this.userRepository = userRepository;
        this.permissionService = permissionService;
    }

    @Transactional
    public void execute(ClassUserId classUserId, long idUser) {
        UserEntity userAuthenticated = userRepository.findById(idUser)
                .orElseThrow(() -> new UserNotFoundDomainException(idUser));

        if (!permissionService.canModifyClassUser(userAuthenticated.getRole())) {
            throw new UserWithoutCreateClassUserPermissionException();
        }

        if (!classUserRepository.existById(classUserId)) {
            throw new ClassUserNotFoundException();
        }

        classUserRepository.deleteById(classUserId);
    }

}