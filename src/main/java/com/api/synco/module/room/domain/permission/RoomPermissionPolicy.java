package com.api.synco.module.room.domain.permission;

import com.api.synco.module.permission.domain.policies.PermissionPolicy;
import com.api.synco.module.user.domain.enumerator.RoleUser;

public class RoomPermissionPolicy implements PermissionPolicy {

    @Override
    public boolean canEdit(RoleUser roleUser) {
        return roleUser.equals(RoleUser.ADMIN);
    }

    @Override
    public boolean canCreate(RoleUser roleUser) {
        return roleUser.equals(RoleUser.ADMIN);
    }

    @Override
    public boolean canDelete(RoleUser roleUser) {
        return roleUser.equals(RoleUser.ADMIN);
    }

}
