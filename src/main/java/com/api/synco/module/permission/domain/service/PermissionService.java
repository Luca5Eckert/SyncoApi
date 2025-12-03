package com.api.synco.module.permission.domain.service;

import com.api.synco.module.permission.domain.policies.PermissionPolicy;
import com.api.synco.module.user.domain.enumerator.RoleUser;
import org.springframework.stereotype.Service;

/**
 * Service for evaluating user permissions.
 *
 * <p>This service provides a facade for permission checking operations,
 * delegating to the configured {@link PermissionPolicy} implementation.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see PermissionPolicy
 */
@Service
public class PermissionService {

    private final PermissionPolicy permissionPolicy;

    /**
     * Constructs a new permission service.
     *
     * @param permissionPolicy the permission policy to delegate to
     */
    public PermissionService(PermissionPolicy permissionPolicy) {
        this.permissionPolicy = permissionPolicy;
    }

    /**
     * Checks if a user can modify class entities.
     *
     * @param roleUser the user's role
     * @return {@code true} if modification is allowed
     */
    public boolean canModifyClass(RoleUser roleUser){
        return permissionPolicy.canModifyClass(roleUser);
    }

    /**
     * Checks if a user can modify course entities.
     *
     * @param roleUser the user's role
     * @return {@code true} if modification is allowed
     */
    public boolean canModifyCourse(RoleUser roleUser){
        return permissionPolicy.canModifyCourse(roleUser);
    }

    /**
     * Checks if a user can modify user entities.
     *
     * @param roleUser the user's role
     * @return {@code true} if modification is allowed
     */
    public boolean canModifyUser(RoleUser roleUser){
        return permissionPolicy.canModifyUser(roleUser);
    }

    /**
     * Checks if a user can modify class-user associations.
     *
     * @param role the user's role
     * @return {@code true} if modification is allowed
     */
    public boolean canModifyClassUser(RoleUser role) {
        return permissionPolicy.canModifyClassUser(role);
    }
}
