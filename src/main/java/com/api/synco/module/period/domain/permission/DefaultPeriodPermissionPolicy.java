package com.api.synco.module.period.domain.permission;

import com.api.synco.module.class_user.domain.enumerator.TypeUserClass;
import com.api.synco.module.user.domain.enumerator.RoleUser;
import org.springframework.stereotype.Component;

import static com.api.synco.module.class_user.domain.enumerator.TypeUserClass.TEACHER;

@Component
public class DefaultPeriodPermissionPolicy implements PeriodPermissionPolicy {

    @Override
    public boolean canCreate(TypeUserClass userClassType, TypeUserClass teacherClassType, RoleUser roleUser) {
        boolean teacherIsValid = (teacherClassType == TypeUserClass.TEACHER);
        if (!teacherIsValid) return false;

        if (roleUser == RoleUser.ADMIN) return true;

        return switch (userClassType) {
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
