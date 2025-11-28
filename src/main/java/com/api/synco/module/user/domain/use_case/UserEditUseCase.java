package com.api.synco.module.user.domain.use_case;

import com.api.synco.module.permission.domain.service.PermissionService;
import com.api.synco.module.user.application.dto.edit.UserEditRequest;
import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.exception.UserNotFoundDomainException;
import com.api.synco.module.user.domain.exception.permission.UserWithoutEditUserPermissionException;
import com.api.synco.module.user.domain.port.UserRepository;
import com.api.synco.module.user.domain.vo.Email;
import com.api.synco.module.user.domain.vo.Name;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserEditUseCase {

    private final PermissionService permissionService;

    private final UserRepository userRepository;

    public UserEditUseCase(PermissionService permissionService, UserRepository userRepository) {
        this.permissionService = permissionService;
        this.userRepository = userRepository;
    }

    /**
     * This Method is responsible for executing the
     * use case of edit a user
     *
     * <p> In this use case will happen the validation if user
     * can edit another, or himself, user. After the validation
     * the user will be edit and save in Database.</p>
     *
     * @param userEditRequest Data of the edit
     * @param idUserAutenticated id of the user who will edit
     * @return The user edited
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

    private void editUser(UserEntity userEdit, UserEditRequest userEditRequest) {
        Name name = new Name(userEditRequest.name());
        Email email = new Email(userEditRequest.email());

        userEdit.setName(name);
        userEdit.setEmail(email);
    }

    private boolean canEditUser(UserEntity userAutenticated, UserEntity userEdit) {
        if(permissionService.canModifyUser(userAutenticated.getRole())) return true;

        return userEdit.equals(userAutenticated);
    }

}
