package com.api.synco.shared.security.user_details;

import com.api.synco.module.user.domain.exception.UserNotFoundDomainException;
import com.api.synco.module.user.domain.port.UserRepository;
import com.api.synco.module.user.domain.vo.Email;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementation of Spring Security's {@link UserDetailsService} interface.
 *
 * <p>This service is responsible for loading user-specific data during
 * authentication. It bridges the domain layer's user repository with
 * Spring Security's authentication mechanism.</p>
 *
 * <p>The service:</p>
 * <ul>
 *   <li>Retrieves user entities from the repository by email</li>
 *   <li>Converts domain entities to Spring Security user details</li>
 *   <li>Throws appropriate exceptions when users are not found</li>
 * </ul>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see UserDetailsService
 * @see UserDetailsMapper
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserDetailsMapper userDetailsMapper;

    /**
     * Constructs a new user details service.
     *
     * @param userRepository the repository for accessing user data
     * @param userDetailsMapper the mapper for converting entities to user details
     */
    public UserDetailsServiceImpl(UserRepository userRepository, UserDetailsMapper userDetailsMapper) {
        this.userRepository = userRepository;
        this.userDetailsMapper = userDetailsMapper;
    }

    /**
     * Loads user details by the user's email address.
     *
     * <p>This method is called by Spring Security during authentication to
     * retrieve user information for credential verification.</p>
     *
     * @param email the email address identifying the user
     * @return the {@link UserDetails} for the specified user
     * @throws UsernameNotFoundException if no user is found with the given email
     * @throws UserNotFoundDomainException if the user is not found in the repository
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(new Email(email)).orElseThrow( () -> new UserNotFoundDomainException(email));
        return userDetailsMapper.toEntity(user);
    }

}
