package com.api.synco.module.period.domain.permission;

import com.api.synco.module.class_user.domain.enumerator.TypeUserClass;
import com.api.synco.module.user.domain.enumerator.RoleUser;
import org.springframework.stereotype.Component;

@Component
public class DefaultPeriodPermissionPolicy implements PeriodPermissionPolicy {

    @Override
    public boolean canCreate(TypeUserClass typeUserClass, RoleUser roleUser) {
        if (roleUser == RoleUser.ADMIN) return true;

        return switch (typeUserClass) {
            case TEACHER, REPRESENTATIVE, ADMINISTRATOR -> true;
            default -> false;
        };
    }

    @Override
    public boolean canDelete(TypeUserClass typeUserClass, RoleUser roleUser) {
        if (roleUser == RoleUser.ADMIN) return true;

        return switch (typeUserClass) {
            case TEACHER, REPRESENTATIVE, ADMINISTRATOR -> true;
            default -> false;
        };
    }
}
