package com.api.synco.module.user.domain.use_case;

import com.api.synco.module.permission.domain.service.PermissionService;
import com.api.synco.module.user.application.dto.edit.UserEditRequest;
import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.enumerator.RoleUser;
import com.api.synco.module.user.domain.exception.permission.UserWithoutEditPermissionDomainException;
import com.api.synco.module.user.domain.port.UserRepository;
import com.api.synco.module.user.domain.vo.Email;
import com.api.synco.module.user.domain.vo.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserEditUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PermissionService permissionService;

    @InjectMocks
    private UserEditUseCase userEditUseCase;

    private long idAuthenticateUser;
    private UserEntity authenticateUser;

    private long id;
    private String name;
    private String email;
    private UserEditRequest userEditRequest;
    private UserEntity userToEdit;

    @BeforeEach
    public void setup(){
        id = 1L;
        name = "kaladin";
        email = "kaladin@gmail.com";
        userEditRequest = new UserEditRequest(id, name, email);

        userToEdit = new UserEntity(id, new Name("lucas"), new Email("email@gmail.com"), null, RoleUser.USER);

        idAuthenticateUser = 2L;
        authenticateUser = new UserEntity(idAuthenticateUser, null, null, null, RoleUser.ADMIN);
    }

    @Test
    public void shouldEditUserWithSuccessWhenUserHavePermission(){
        //arrange
        when(userRepository.findById(id)).thenReturn(Optional.of(userToEdit));
        when(userRepository.findById(idAuthenticateUser)).thenReturn(Optional.of(authenticateUser));
        when(permissionService.canModifyUser(any(RoleUser.class))).thenReturn(true);

        //act
        var user = userEditUseCase.execute(userEditRequest, idAuthenticateUser);

        //assert -- verify the returned user
        assertThat(user.getName().value()).isEqualTo(name);
        assertThat(user.getEmail().address()).isEqualTo(email);

        //assert - verify the user saved
        var captor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(captor.capture());
        var edited = captor.getValue();
        assertThat(edited.getName()).isEqualTo(user.getName());
        assertThat(edited.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void shouldEditUserWithSuccessWhenUserIsEditHimself(){
        //arrange
        when(userRepository.findById(id)).thenReturn(Optional.of(userToEdit));

        //act
        var user = userEditUseCase.execute(userEditRequest, id);

        //assert -- verify the returned user
        assertThat(user.getName().value()).isEqualTo(name);
        assertThat(user.getEmail().address()).isEqualTo(email);

        //assert - verify the user saved
        var captor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(captor.capture());
        var edited = captor.getValue();
        assertThat(edited.getName()).isEqualTo(user.getName());
        assertThat(edited.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void shouldThrowExceptionWhenUserDoNotHavePermission(){
        //create the user without permission
        var userWithoutPermission = new UserEntity(idAuthenticateUser, null, null, null, RoleUser.USER);

        //arrange
        when(userRepository.findById(id)).thenReturn(Optional.of(userToEdit));
        when(userRepository.findById(idAuthenticateUser)).thenReturn(Optional.of(userWithoutPermission));

        //act and assert
        assertThatThrownBy(() -> userEditUseCase.execute(userEditRequest, idAuthenticateUser))
                .isExactlyInstanceOf(UserWithoutEditPermissionDomainException.class);

    }



}