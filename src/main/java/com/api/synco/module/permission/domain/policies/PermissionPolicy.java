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
 * @see com.api.synco.module.room.domain.permission.RoomPermissionPolicy
 * @see RoleUser
 */
public interface PermissionPolicy {

    boolean canEdit(RoleUser roleUser);
    boolean canCreate(RoleUser roleUser);
    boolean canDelete(RoleUser roleUser);

}
