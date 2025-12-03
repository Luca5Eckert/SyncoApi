package com.api.synco.module.authentication.domain.use_case;

import com.api.synco.infrastructure.security.jwt.JwtTokenProvider;
import com.api.synco.infrastructure.security.user_details.UserDetailsImpl;
import com.api.synco.module.authentication.application.dto.login.UserLoginRequest;
import com.api.synco.module.authentication.application.dto.login.UserLoginResponse;
import com.api.synco.module.authentication.domain.exception.AuthenticationValidationException;
import com.api.synco.module.authentication.domain.mapper.AuthenticationMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Use case for user login authentication.
 *
 * <p>This use case handles the authentication process including:</p>
 * <ul>
 *   <li>Credential validation via Spring Security</li>
 *   <li>JWT token generation for authenticated users</li>
 *   <li>Response mapping with user details and token</li>
 * </ul>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see AuthenticationManager
 * @see JwtTokenProvider
 */
@Component
public class UserLoginUseCase {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationMapper authenticationMapper;

    /**
     * Constructs a new user login use case.
     *
     * @param authenticationManager the Spring Security authentication manager
     * @param jwtTokenProvider the provider for JWT token operations
     * @param authenticationMapper the mapper for response creation
     */
    public UserLoginUseCase(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, AuthenticationMapper authenticationMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationMapper = authenticationMapper;
    }


    /**
     * Executes the user login use case.
     *
     * <p>Validates the provided credentials and generates a JWT token
     * for successful authentication.</p>
     *
     * @param userLoginRequest the request containing login credentials
     * @return the login response with user details and JWT token
     * @throws AuthenticationValidationException if authentication fails
     */
    public UserLoginResponse execute(UserLoginRequest userLoginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequest.email(), userLoginRequest.password()));

        if(!authentication.isAuthenticated()){
            throw new AuthenticationValidationException();
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String token = jwtTokenProvider.generateToken(userDetails.getUsername());

        return authenticationMapper.toLoginResponse(userDetails, token);
    }

}
