package com.api.synco.module.authentication.domain.use_case;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.api.synco.core.security.jwt.JwtTokenProvider;
import com.api.synco.core.security.user_details.UserDetailsImpl;
import com.api.synco.module.authentication.application.dto.login.UserLoginRequest;
import com.api.synco.module.authentication.domain.exception.AuthenticationValidationException;
import com.api.synco.module.authentication.domain.mapper.AuthenticationMapper;
import com.api.synco.module.user.domain.enumerator.RoleUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
class UserLoginUseCaseTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private AuthenticationMapper authenticationMapper;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserLoginUseCase userLoginUseCase;

    private UserLoginRequest request;
    private String email;
    private String password;
    private UserDetailsImpl userDetails;

    @BeforeEach
    public void setup(){
        email = "john@example.com";
        password = "Strong#Pass123";
        request = new UserLoginRequest(email, password);
        userDetails = new UserDetailsImpl(1L, email, "encodedPassword", RoleUser.USER);
    }

    @DisplayName("Should login successfully with valid credentials")
    @Test
    public void shouldLoginSuccessfully(){
        //arrange
        String expectedToken = "jwt-token-12345";
        
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtTokenProvider.generateToken(email)).thenReturn(expectedToken);

        //act
        var response = userLoginUseCase.execute(request);

        //assert
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenProvider).generateToken(email);
        verify(authenticationMapper).toLoginResponse(userDetails, expectedToken);
    }

    @DisplayName("Should throw AuthenticationValidationException when authentication fails")
    @Test
    public void shouldThrowAuthenticationValidationExceptionWhenNotAuthenticated(){
        //arrange
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);

        //act and assert
        assertThatThrownBy(() -> userLoginUseCase.execute(request))
                .isInstanceOf(AuthenticationValidationException.class);

        verify(jwtTokenProvider, never()).generateToken(any());
        verify(authenticationMapper, never()).toLoginResponse(any(), any());
    }
}
