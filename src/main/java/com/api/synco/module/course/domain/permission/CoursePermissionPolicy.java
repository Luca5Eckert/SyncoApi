package com.api.synco.module.course.domain.permission;

import com.api.synco.module.permission.domain.policies.PermissionPolicy;
import com.api.synco.module.user.domain.enumerator.RoleUser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("coursePermissionPolicy")
public class CoursePermissionPolicy implements PermissionPolicy {

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
