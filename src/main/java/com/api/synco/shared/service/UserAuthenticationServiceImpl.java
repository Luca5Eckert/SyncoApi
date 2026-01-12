package com.api.synco.shared.service;

import com.api.synco.shared.core.UserAuthenticationService;
import com.api.synco.shared.security.user_details.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link UserAuthenticationService} interface.
 *
 * <p>This service provides access to the currently authenticated user's information
 * by extracting data from Spring Security's security context. It serves as a
 * bridge between the security layer and application business logic.</p>
 *
 * <p>The service retrieves authentication information from the
 * {@link SecurityContextHolder}, which stores the current user's security
 * context in a thread-local variable.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see UserAuthenticationService
 * @see SecurityContextHolder
 */
@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    /**
     * Retrieves the unique identifier of the currently authenticated user.
     *
     * <p>This method extracts the user ID from the {@link UserDetailsImpl}
     * stored in the security context.</p>
     *
     * @return the unique identifier of the authenticated user
     * @throws ClassCastException if the principal is not an instance of {@link UserDetailsImpl}
     */
    @Override
    public long getAuthenticatedUserId() {
        var userDetails = (UserDetailsImpl) getUserDetails();

        return userDetails.getId();
    }

    /**
     * Retrieves the {@link UserDetails} for the currently authenticated user.
     *
     * <p>This method extracts the principal from the current security context's
     * authentication object.</p>
     *
     * @return the {@link UserDetails} of the authenticated user
     * @throws NullPointerException if no authentication is present in the context
     */
    @Override
    public UserDetails getUserDetails() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
