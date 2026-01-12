package com.api.synco.modules.class_user.domain.command;

import com.api.synco.module.class_user.domain.enumerator.TypeUserClass;

/**
 * Command record for updating a class-user association.
 *
 * <p>This is a pure Java record used to pass data to the use case layer.
 * It contains no framework dependencies and is used instead of Web DTOs
 * to maintain domain purity.</p>
 *
 * @param newTypeUserClass the new type of user-class association
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 */
public record UpdateClassUserCommand(
        TypeUserClass newTypeUserClass
) {
}
