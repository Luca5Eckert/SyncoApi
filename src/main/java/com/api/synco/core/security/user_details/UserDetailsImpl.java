package com.api.synco.core.security.user_details;

import com.api.synco.module.user.domain.enumerator.RoleUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Custom implementation of Spring Security's {@link UserDetails} interface.
 *
 * <p>This class adapts the application's user entity to Spring Security's
 * authentication mechanism. It contains the essential user information
 * required for authentication and authorization.</p>
 *
 * <p>The implementation provides:</p>
 * <ul>
 *   <li>User identification via email (used as username)</li>
 *   <li>Password verification support</li>
 *   <li>Role-based authorities</li>
 *   <li>User ID for application-level operations</li>
 * </ul>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see UserDetails
 * @see UserDetailsMapper
 */
public class UserDetailsImpl implements UserDetails {

    private final long id;
    private final String email;
    private final String password;
    private final RoleUser roleUser;

    /**
     * Constructs a new user details instance.
     *
     * @param id the unique identifier of the user
     * @param email the user's email (used as username)
     * @param password the user's encoded password
     * @param roleUser the user's role for authorization
     */
    public UserDetailsImpl(long id, String email, String password, RoleUser roleUser) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.roleUser = roleUser;
    }

    /**
     * Returns the authorities granted to the user.
     *
     * <p>The user's role is returned as a single-element collection,
     * as each user has exactly one role in this implementation.</p>
     *
     * @return a collection containing the user's role as a granted authority
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(roleUser);
    }

    /**
     * Returns the encoded password used to authenticate the user.
     *
     * @return the user's encoded password
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Returns the username used to authenticate the user.
     *
     * <p>In this implementation, the email address is used as the username.</p>
     *
     * @return the user's email address
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Returns the unique identifier of the user.
     *
     * <p>This is an extension to the standard {@link UserDetails} interface,
     * providing access to the user's database ID for application operations.</p>
     *
     * @return the user's unique identifier
     */
    public long getId(){
        return id;
    }

}
