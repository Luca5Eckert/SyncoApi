package com.api.synco.module.permission.domain.policies;

import com.api.synco.module.user.domain.enumerator.RoleUser;

public interface PermissionPolicy {

    boolean canModifyClass(RoleUser roleUser);

    boolean canModifyCourse(RoleUser roleUser);

    boolean canModifyUser(RoleUser roleUser);

}
