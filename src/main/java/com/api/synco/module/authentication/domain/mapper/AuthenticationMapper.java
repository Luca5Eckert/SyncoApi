package com.api.synco.module.authentication.domain.mapper;

import com.api.synco.infrastructure.security.user_details.UserDetailsImpl;
import com.api.synco.module.authentication.application.dto.login.UserLoginResponse;
import com.api.synco.module.authentication.application.dto.register.UserRegisterResponse;
import com.api.synco.module.user.domain.UserEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper component for converting authentication-related domain objects to DTOs.
 *
 * <p>This class provides mapping methods to transform domain entities and
 * user details into response DTOs used by the API layer.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class AuthenticationMapper {

    /**
     * Maps a user entity to a registration response DTO.
     *
     * @param user the user entity to map
     * @return a {@link UserRegisterResponse} containing the user's data
     */
    public UserRegisterResponse toRegisterResponse(UserEntity user) {
        return new UserRegisterResponse(user.getId(), user.getName().value(), user.getEmail().address(), user
                .getRole());
    }

    /**
     * Maps user details and token to a login response DTO.
     *
     * @param user the user details to map
     * @param token the JWT token to include
     * @return a {@link UserLoginResponse} containing authentication data
     */
    public UserLoginResponse toLoginResponse(UserDetailsImpl user, String token) {
        return new UserLoginResponse(
                user.getId()
                , user.getUsername()
                , user.getAuthorities()
                , token);
    }
}
