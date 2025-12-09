package com.api.synco.module.permission.domain.policies;

import com.api.synco.module.user.domain.enumerator.RoleUser;

/**
 * Interface defining permission policies for system operations.
 *
 * <p>This interface declares methods for checking user permissions across
 * different domain operations. Implementations define the actual permission
 * rules based on user roles.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see DefaultPermissionPolicy
 * @see RoleUser
 */
public interface PermissionPolicy {

    /**
     * Checks if a user with the given role can modify class entities.
     *
     * @param roleUser the user's role
     * @return {@code true} if modification is allowed, {@code false} otherwise
     */
    boolean canModifyClass(RoleUser roleUser);

    /**
     * Checks if a user with the given role can modify course entities.
     *
     * @param roleUser the user's role
     * @return {@code true} if modification is allowed, {@code false} otherwise
     */
    boolean canModifyCourse(RoleUser roleUser);

    /**
     * Checks if a user with the given role can modify user entities.
     *
     * @param roleUser the user's role
     * @return {@code true} if modification is allowed, {@code false} otherwise
     */
    boolean canModifyUser(RoleUser roleUser);

    /**
     * Checks if a user with the given role can modify class-user associations.
     *
     * @param role the user's role
     * @return {@code true} if modification is allowed, {@code false} otherwise
     */
    boolean canModifyClassUser(RoleUser role);

    /**
     * Checks if a user with the given role can modify room entity.
     *
     * @param role the user's role
     * @return {@code true} if modification is allowed, {@code false} otherwise
     */
    boolean canModifyRoom(RoleUser role);
}
