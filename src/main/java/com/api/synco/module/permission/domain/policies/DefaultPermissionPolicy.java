package com.api.synco.module.permission.domain.policies;

import com.api.synco.module.user.domain.enumerator.RoleUser;
import org.springframework.stereotype.Component;

/**
 * Default implementation of the {@link PermissionPolicy} interface.
 *
 * <p>This implementation defines the standard permission rules where only
 * administrators (ADMIN role) can modify system entities.</p>
 *
 * <p>Permission rules:</p>
 * <ul>
 *   <li>ADMIN - Full access to all modification operations</li>
 *   <li>USER - No modification permissions</li>
 * </ul>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see PermissionPolicy
 */
@Component
public class DefaultPermissionPolicy implements PermissionPolicy {

    /**
     * {@inheritDoc}
     *
     * <p>Only administrators can modify class entities.</p>
     */
    @Override
    public boolean canModifyClass(RoleUser roleUser) {
        return roleUser == RoleUser.ADMIN;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Only administrators can modify course entities.</p>
     */
    @Override
    public boolean canModifyCourse(RoleUser roleUser) {
        return roleUser == RoleUser.ADMIN;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Only administrators can modify user entities.</p>
     */
    @Override
    public boolean canModifyUser(RoleUser roleUser) {
        return roleUser == RoleUser.ADMIN;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Only administrators can modify class-user associations.</p>
     */
    @Override
    public boolean canModifyClassUser(RoleUser roleUser) {
        return roleUser == RoleUser.ADMIN;
    }


}
