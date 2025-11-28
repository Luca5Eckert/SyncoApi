package com.api.synco.module.user.domain.use_case;

import com.api.synco.module.permission.domain.service.PermissionService;
import com.api.synco.module.user.application.dto.delete.UserDeleteRequest;
import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.enumerator.RoleUser;
import com.api.synco.module.user.domain.exception.UserNotFoundDomainException;
import com.api.synco.module.user.domain.exception.permission.UserWithoutDeleteUserPermissionException;
import com.api.synco.module.user.domain.port.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDeleteUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PermissionService permissionService;

    @InjectMocks
    private UserDeleteUseCase userDeleteUseCase;

    private long id;
    private UserEntity userEntity;

    private UserDeleteRequest userDeleteRequest;

    private long idUserAuthentication;
    private UserEntity userAuthenticated;

    @BeforeEach
    void setup() {
        id = 1L;
        userEntity = new UserEntity();

        userDeleteRequest = new UserDeleteRequest(id);

        idUserAuthentication = 2L;
        userAuthenticated = new UserEntity(null, null, null, RoleUser.ADMIN);
    }

    @Test
    public void shouldDeleteUserWhenExists(){
        when(userRepository.existsById(id)).thenReturn(true);
        when(userRepository.findById(idUserAuthentication)).thenReturn(Optional.of(userAuthenticated));
        when(permissionService.canModifyUser(any(RoleUser.class))).thenReturn(true);

        userDeleteUseCase.execute(userDeleteRequest, idUserAuthentication);

        verify(userRepository).deleteById(id);
    }

    @Test
    void shouldThrowWhenUserNotExist() {
        when(userRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> userDeleteUseCase.execute(userDeleteRequest, idUserAuthentication))
                .isInstanceOf(UserNotFoundDomainException.class);

        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    void shouldThrowWhenUserDontHavePermission() {
        var userWithoutPermission = new UserEntity(null, null, null, RoleUser.USER);

        //arrange
        when(userRepository.existsById(id)).thenReturn(true);
        when(userRepository.findById(idUserAuthentication)).thenReturn(Optional.of(userWithoutPermission));

        //act and assert
        assertThatThrownBy(() -> userDeleteUseCase.execute(userDeleteRequest, idUserAuthentication))
                .isExactlyInstanceOf(UserWithoutDeleteUserPermissionException.class);

    }

}