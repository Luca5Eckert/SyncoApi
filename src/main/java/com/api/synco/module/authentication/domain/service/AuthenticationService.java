package com.api.synco.module.authentication.domain.service;

import com.api.synco.module.authentication.application.dto.login.UserLoginRequest;
import com.api.synco.module.authentication.application.dto.login.UserLoginResponse;
import com.api.synco.module.authentication.application.dto.register.UserRegisterRequest;
import com.api.synco.module.authentication.application.dto.register.UserRegisterResponse;
import com.api.synco.module.authentication.application.dto.reset_password.UserResetRequest;
import com.api.synco.module.authentication.domain.mapper.AuthenticationMapper;
import com.api.synco.module.authentication.domain.use_case.UserLoginUseCase;
import com.api.synco.module.authentication.domain.use_case.UserRegisterUseCase;
import com.api.synco.module.authentication.domain.use_case.UserResetPasswordUseCase;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

/**
 * Domain service for authentication operations.
 *
 * <p>This service coordinates authentication-related business operations by
 * delegating to appropriate use cases and handling response mapping.</p>
 *
 * <p>Responsibilities:</p>
 * <ul>
 *   <li>User registration with password validation</li>
 *   <li>User authentication and JWT token generation</li>
 *   <li>Password reset for authenticated users</li>
 * </ul>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see UserRegisterUseCase
 * @see UserLoginUseCase
 * @see UserResetPasswordUseCase
 */
@Service
public class AuthenticationService {

    private final AuthenticationMapper authenticationMapper;

    private final UserRegisterUseCase registerUseCase;
    private final UserLoginUseCase loginUseCase;
    private final UserResetPasswordUseCase userResetPasswordUseCase;

    /**
     * Constructs a new authentication service.
     *
     * @param authenticationMapper the mapper for converting entities to DTOs
     * @param registerUseCase the use case for user registration
     * @param loginUseCase the use case for user login
     * @param userResetPasswordUseCase the use case for password reset
     */
    public AuthenticationService(AuthenticationMapper authenticationMapper, UserRegisterUseCase registerUseCase, UserLoginUseCase loginUseCase, UserResetPasswordUseCase userResetPasswordUseCase) {
        this.authenticationMapper = authenticationMapper;
        this.registerUseCase = registerUseCase;
        this.loginUseCase = loginUseCase;
        this.userResetPasswordUseCase = userResetPasswordUseCase;
    }

    /**
     * Registers a new user in the system.
     *
     * @param userRegisterRequest the request containing registration data
     * @return the registered user's data
     */
    public UserRegisterResponse register(@Valid UserRegisterRequest userRegisterRequest) {
        var user = registerUseCase.execute(userRegisterRequest);
        return authenticationMapper.toRegisterResponse(user);
    }

    /**
     * Authenticates a user and returns a JWT token.
     *
     * @param userLoginRequest the request containing login credentials
     * @return the login response with JWT token
     */
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        return loginUseCase.execute(userLoginRequest);
    }

    /**
     * Resets the password for the specified user.
     *
     * @param userResetRequest the request containing current and new passwords
     * @param idUser the ID of the user requesting the password reset
     */
    public void resetPassword(UserResetRequest userResetRequest, long idUser) {
        userResetPasswordUseCase.execute(userResetRequest, idUser);
    }

}
