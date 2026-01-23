package com.api.synco.module.user.domain.use_case;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.api.synco.module.permission.domain.policies.PermissionPolicy;
import com.api.synco.module.user.application.dto.create.UserCreateRequest;
import com.api.synco.module.user.domain.UserEntity;
import com.api.synco.module.user.domain.enumerator.RoleUser;
import com.api.synco.module.user.domain.exception.email.EmailNotUniqueDomainException;
import com.api.synco.module.user.domain.exception.password.PasswordNotValidDomainException;
import com.api.synco.module.user.domain.port.UserRepository;
import com.api.synco.module.user.domain.validator.PasswordValidatorImpl;
import com.api.synco.module.user.domain.vo.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserCreateUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private PasswordValidatorImpl passwordValidator;

    @Mock
    private PermissionPolicy permissionPolicy ;

    @InjectMocks
    private UserCreateUseCase userCreateUseCase;
    
    private UserCreateRequest request;
    private String name;
    private String email;
    private String password;
    private RoleUser roleUser;

    @BeforeEach
    public void setup(){
        name = "lucas";
        email = "lucas@gmail.com";
        password = "Lucas#113";
        roleUser = RoleUser.USER;
        request = new UserCreateRequest(name, email, password, roleUser);
    }
    
    @DisplayName("This method execute should create user")
    @Test
    public void shouldCreateUser(){
        //arrange
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(new UserEntity(-1, null, null, null, RoleUser.ADMIN)));
        when(permissionPolicy .canCreate (any(RoleUser.class))).thenReturn(true);
        when(passwordEncoder.encode(password)).thenReturn("hash");
        when(passwordValidator.isValid(password)).thenReturn(true);
        when(userRepository.existsByEmail(any(Email.class))).thenReturn(false);

        //act
        var user = userCreateUseCase.execute(request, -1);

        //assert -- returned entity has expected email and encoded passowod
        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isNotNull();
        assertThat(user.getEmail().address()).isEqualTo(email);
        assertThat(user.getPassword()).isEqualTo("hash");
        assertThat(user.getRole()).isEqualTo(roleUser);

        //assert -= repository.save recive the same UserEntity
        var captor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(captor.capture());
        var saved = captor.getValue();
        assertThat(saved).isNotNull();
        assertThat(saved.getName()).isEqualTo(user.getName());
        assertThat(saved.getEmail()).isEqualTo(user.getEmail());
        assertThat(saved.getRole()).isEqualTo(user.getRole());
    }

    @DisplayName("The method execute should throw a EmailNotUniqueDomainException")
    @Test
    public void shouldThrowEmailNotUniqueException(){
        //arrange
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(new UserEntity(-1, null, null, null, RoleUser.ADMIN)));
        when(permissionPolicy .canCreate (any(RoleUser.class))).thenReturn(true);
        when(passwordEncoder.encode(password)).thenReturn("hash");
        when(passwordValidator.isValid(password)).thenReturn(true);
        when(userRepository.existsByEmail(new Email(email))).thenReturn(true);

        //act and assert
        assertThatThrownBy( () -> userCreateUseCase.execute(request, -1))
                .isInstanceOf(EmailNotUniqueDomainException.class);

        verify(userRepository, never()).save(any());

    }

    @DisplayName("Should Throw PasswordNotValidDomainException")
    @Test
    public void shouldThrowPasswordNotValidException(){
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(new UserEntity(-1, null, null, null, RoleUser.ADMIN)));
        when(permissionPolicy .canCreate (any(RoleUser.class))).thenReturn(true);
        when(passwordValidator.isValid(password)).thenReturn(false);

        assertThatThrownBy( () -> userCreateUseCase.execute(request, -1))
                .isExactlyInstanceOf(PasswordNotValidDomainException.class);

        verify(passwordEncoder, never()).encode(any());
        verify(userRepository, never()).existsByEmail(any());
        verify(userRepository, never()).save(any());
    }

}