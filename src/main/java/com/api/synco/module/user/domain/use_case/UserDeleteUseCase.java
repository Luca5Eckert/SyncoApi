package com.api.synco.module.user.domain.use_case;

import com.api.synco.module.permission.domain.service.PermissionService;
import com.api.synco.module.user.application.dto.delete.UserDeleteRequest;
import com.api.synco.module.user.domain.exception.UserNotFoundDomainException;
import com.api.synco.module.user.domain.exception.permission.UserWithoutDeletePermissionDomainException;
import com.api.synco.module.user.domain.port.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

@Component
public class UserDeleteUseCase {

    private final PermissionService permissionService;

    private final UserRepository userRepository;

    public UserDeleteUseCase(PermissionService permissionService, UserRepository userRepository) {
        this.permissionService = permissionService;
        this.userRepository = userRepository;
    }

    public void execute(@Valid UserDeleteRequest userDeleteRequest, long idUserAutenticated) {

        if(!userRepository.existsById(userDeleteRequest.id())) throw new UserNotFoundDomainException(userDeleteRequest.id());

        var userAuthenticated = userRepository.findById(idUserAutenticated).orElseThrow(() -> new UserNotFoundDomainException(idUserAutenticated));

        if(!permissionService.canModifyUser(userAuthenticated.getRole())) {
            throw new UserWithoutDeletePermissionDomainException();
        }

        userRepository.deleteById(userDeleteRequest.id());

    }

}
