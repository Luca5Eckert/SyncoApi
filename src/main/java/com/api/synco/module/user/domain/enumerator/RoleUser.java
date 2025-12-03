package com.api.synco.module.user.domain.enumerator;

import org.springframework.security.core.GrantedAuthority;

/**
 * Enumeration representing user roles in the system.
 *
 * <p>This enum implements {@link GrantedAuthority} to integrate with
 * Spring Security's authorization mechanism. Each role has an associated
 * authority string prefixed with "ROLE_" as per Spring Security conventions.</p>
 *
 * <p>Available roles:</p>
 * <ul>
 *   <li>{@link #USER} - Standard user with limited permissions</li>
 *   <li>{@link #ADMIN} - Administrator with full system access</li>
 * </ul>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see GrantedAuthority
 */
public enum RoleUser implements GrantedAuthority {
    /**
     * Standard user role with limited permissions.
     */
    USER("ROLE_USER"),
    
    /**
     * Administrator role with full system access.
     */
    ADMIN("ROLE_ADMIN");

    private final String authority;

    /**
     * Constructs a role with the specified authority string.
     *
     * @param authority the Spring Security authority string
     */
    RoleUser(String authority) {
        this.authority = authority;
    }

    /**
     * Returns the authority string for this role.
     *
     * @return the authority string (e.g., "ROLE_ADMIN")
     */
    @Override
    public String getAuthority() {
        return authority;
    }

}
