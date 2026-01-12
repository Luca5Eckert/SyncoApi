package com.api.synco.shared.core;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Service interface for accessing authenticated user information.
 *
 * <p>This interface provides methods to retrieve information about the currently
 * authenticated user from the Spring Security context. It abstracts the security
 * context access, making it easier to obtain user details throughout the application.</p>
 *
 * <p>Implementations of this interface should handle the extraction of user information
 * from the security context in a thread-safe manner.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see org.springframework.security.core.userdetails.UserDetails
 */
public interface UserAuthenticationService {

    /**
     * Retrieves the unique identifier of the currently authenticated user.
     *
     * <p>This method extracts the user ID from the security context. It should be
     * called only when a user is authenticated, otherwise it may throw an exception.</p>
     *
     * @return the unique identifier of the authenticated user
     * @throws ClassCastException if the principal is not of the expected type
     * @throws NullPointerException if no authentication is present in the security context
     */
    long getAuthenticatedUserId();

    /**
     * Retrieves the {@link UserDetails} object for the currently authenticated user.
     *
     * <p>This method returns the complete user details including username, authorities,
     * and account status flags from the security context.</p>
     *
     * @return the {@link UserDetails} of the authenticated user
     * @throws ClassCastException if the principal is not of the expected type
     * @throws NullPointerException if no authentication is present in the security context
     */
    UserDetails getUserDetails();

}