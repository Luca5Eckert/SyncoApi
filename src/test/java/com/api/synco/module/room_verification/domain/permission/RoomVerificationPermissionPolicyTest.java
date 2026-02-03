package com.api.synco.module.room_verification.domain.permission;

import com.api.synco.module.class_user.domain.enumerator.TypeUserClass;
import com.api.synco.module.user.domain.enumerator.RoleUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RoomVerificationPermissionPolicyTest {

    private final RoomVerificationPermissionPolicy policy = new RoomVerificationPermissionPolicy();

    @ParameterizedTest
    @EnumSource(TypeUserClass.class)
    @DisplayName("Deve permitir acesso se o usuário for ADMIN, independente do tipo na classe")
    void adminShouldAlwaysHaveAccess(TypeUserClass typeUserClass) {
        assertTrue(policy.canCreate(RoleUser.ADMIN, typeUserClass));
    }

    @ParameterizedTest
    @CsvSource({
            "REPRESENTATIVE, true",
            "TEACHER, true",
            "ADMINISTRATOR, true",
            "SECRETARY, true",
            "STUDENT, false"
    })
    @DisplayName("Deve validar permissões baseadas no tipo de usuário na classe para usuários comuns")
    void shouldValidatePermissionsForCommonUsers(TypeUserClass typeUserClass, boolean expectedResult) {
        // Arrange & Act
        boolean result = policy.canCreate(RoleUser.USER, typeUserClass);

        // Assert
        if (expectedResult) {
            assertTrue(result, "Deveria permitir acesso para: " + typeUserClass);
        } else {
            assertFalse(result, "Não deveria permitir acesso para: " + typeUserClass);
        }
    }
}