package com.api.synco.module.period.domain.permission;

import com.api.synco.module.class_user.domain.enumerator.TypeUserClass;
import com.api.synco.module.user.domain.enumerator.RoleUser;

public interface PeriodPermissionPolicy {

    boolean canCreate(TypeUserClass typeUserClass, TypeUserClass classUserId, RoleUser roleUser);
    boolean canDelete(TypeUserClass typeUserClass, RoleUser roleUser);

}
