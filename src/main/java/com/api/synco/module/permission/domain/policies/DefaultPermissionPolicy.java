package com.api.synco.module.permission.domain.policies;

import com.api.synco.module.user.domain.enumerator.RoleUser;
import org.springframework.stereotype.Component;

@Component
public class DefaultPermissionPolicy implements PermissionPolicy {

    @Override
    public boolean canModifyClass(RoleUser roleUser) {
        return roleUser == RoleUser.ADMIN;
    }

    @Override
    public boolean canModifyCourse(RoleUser roleUser) {
        return roleUser == RoleUser.ADMIN;
    }

    @Override
    public boolean canModifyUser(RoleUser roleUser) {
        return roleUser == RoleUser.ADMIN;
    }


}
