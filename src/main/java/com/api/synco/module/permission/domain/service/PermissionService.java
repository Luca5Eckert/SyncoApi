package com.api.synco.module.permission.domain.service;

import com.api.synco.module.permission.domain.policies.PermissionPolicy;
import com.api.synco.module.user.domain.enumerator.RoleUser;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

    private final PermissionPolicy permissionPolicy;

    public PermissionService(PermissionPolicy permissionPolicy) {
        this.permissionPolicy = permissionPolicy;
    }

    public boolean canModifyClass(RoleUser roleUser){
        return permissionPolicy.canModifyClass(roleUser);
    }

    public boolean canModifyCourse(RoleUser roleUser){
        return permissionPolicy.canModifyCourse(roleUser);
    }

    public boolean canModifyUser(RoleUser roleUser){
        return permissionPolicy.canModifyUser(roleUser);
    }

    public boolean canModifyClassUser(RoleUser role) {
        return permissionPolicy.canModifyClassUser(role);
    }
}
