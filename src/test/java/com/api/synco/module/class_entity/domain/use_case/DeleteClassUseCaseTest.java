package com.api.synco.module.class_entity.domain.use_case;

import com.api.synco.module.class_entity.domain.ClassEntityId;
import com.api.synco.module.class_entity.domain.exception.ClassNotFoundException;
import com.api.synco.module.class_entity.domain.exception.user.UserWithoutDeleteClassPermissionException;
import com.api.synco.module.class_entity.domain.port.ClassRepository;
import com.api.synco.module.permission.domain.policies.PermissionPolicy;
import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.enumerator.RoleUser;
import com.api.synco.module.user.domain.port.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class DeleteClassUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ClassRepository classRepository;

    @Mock
    private PermissionPolicy permissionPolicy;

    @InjectMocks
    private DeleteClassUseCase deleteClassUseCase;



    @Test
    @DisplayName("Should delete class with success")
    public void shouldDeleteClassWithSuccess(){
        when(userRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(new UserEntity(-1, null, null, null, RoleUser.ADMIN)));

        when(permissionPolicy.canDelete(any(RoleUser.class)))
                .thenReturn(true);

        when(classRepository.existById(any(ClassEntityId.class)))
                .thenReturn(true);

        deleteClassUseCase.execute(new ClassEntityId(), -1);

        verify(classRepository).deleteById(any(ClassEntityId.class));

    }

    @Test
    public void shouldThrowExceptionWhenUserDoNotHavePermission(){
        when(userRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(new UserEntity(-1, null, null, null, RoleUser.ADMIN)));

        when(permissionPolicy.canDelete(any(RoleUser.class)))
                .thenReturn(false);

        assertThatThrownBy(() -> deleteClassUseCase.execute(new ClassEntityId(), -1))
                .isExactlyInstanceOf(UserWithoutDeleteClassPermissionException.class);

    }

    @Test
    public void shouldThrowExceptionWhenClassNotExist(){
        when(userRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(new UserEntity(-1, null, null, null, RoleUser.ADMIN)));

        when(permissionPolicy.canDelete(any(RoleUser.class)))
                .thenReturn(true);

        when(classRepository.existById(any(ClassEntityId.class)))
                .thenReturn(false);

        assertThatThrownBy(() -> deleteClassUseCase.execute(new ClassEntityId(), -1))
                .isExactlyInstanceOf(ClassNotFoundException.class);

    }




}