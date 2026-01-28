package com.api.synco.module.period.domain.permission;

import com.api.synco.module.class_user.domain.enumerator.TypeUserClass;
import com.api.synco.module.user.domain.enumerator.RoleUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultPeriodPermissionPolicyTest {

    private final DefaultPeriodPermissionPolicy policy = new DefaultPeriodPermissionPolicy();

    private static final EnumSet<TypeUserClass> ALLOWED_NON_ADMIN =
            EnumSet.of(TypeUserClass.TEACHER, TypeUserClass.REPRESENTATIVE, TypeUserClass.ADMINISTRATOR);

    @Test
    @DisplayName("ADMIN can create regardless of class role")
    void canCreate_adminAlwaysTrue() {
        for (TypeUserClass t : TypeUserClass.values()) {
            assertThat(policy.canCreate(t, RoleUser.ADMIN)).as("create admin with %s", t).isTrue();
        }
    }

    @Test
    @DisplayName("Non-admin create: only TEACHER/REPRESENTATIVE/ADMINISTRATOR are allowed")
    void canCreate_nonAdmin_onlyAllowedSet() {
        for (TypeUserClass t : TypeUserClass.values()) {
            boolean expected = ALLOWED_NON_ADMIN.contains(t);
            assertThat(policy.canCreate(t, RoleUser.USER))
                    .as("create non-admin with %s", t)
                    .isEqualTo(expected);
        }
    }

    @Test
    @DisplayName("ADMIN can delete regardless of class role")
    void canDelete_adminAlwaysTrue() {
        for (TypeUserClass t : TypeUserClass.values()) {
            assertThat(policy.canDelete(t, RoleUser.ADMIN)).as("delete admin with %s", t).isTrue();
        }
    }

    @Test
    @DisplayName("Non-admin delete: only TEACHER/REPRESENTATIVE/ADMINISTRATOR are allowed")
    void canDelete_nonAdmin_onlyAllowedSet() {
        for (TypeUserClass t : TypeUserClass.values()) {
            boolean expected = ALLOWED_NON_ADMIN.contains(t);
            assertThat(policy.canDelete(t, RoleUser.USER))
                    .as("delete non-admin with %s", t)
                    .isEqualTo(expected);
        }
    }
}
