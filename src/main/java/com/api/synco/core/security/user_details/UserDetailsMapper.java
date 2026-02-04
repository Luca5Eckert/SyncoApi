package com.api.synco.core.security.user_details;

import com.api.synco.module.user.domain.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Mapper component for converting domain user entities to Spring Security user details.
 *
 * <p>This class provides mapping functionality between the domain layer's
 * {@link UserEntity} and the security layer's {@link UserDetails} implementation.</p>
 *
 * <p>The mapping extracts:</p>
 * <ul>
 *   <li>User ID for identification</li>
 *   <li>Email address for authentication</li>
 *   <li>Encoded password for verification</li>
 *   <li>User role for authorization</li>
 * </ul>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see UserEntity
 * @see UserDetailsImpl
 */
@Component
public class UserDetailsMapper {

    /**
     * Converts a domain user entity to a Spring Security user details object.
     *
     * @param user the domain user entity to convert
     * @return a {@link UserDetails} instance containing the user's security information
     */
    public UserDetails toEntity(UserEntity user) {
        return new UserDetailsImpl(
                user.getId()
                , user.getEmail().address()
                , user.getPassword()
                , user.getRole());
    }

}
