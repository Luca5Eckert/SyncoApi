package com.api.synco.module.user.application.dto.edit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for user edit requests.
 *
 * <p>This record encapsulates the data required to update an existing
 * user's profile information. Password changes are handled separately.</p>
 *
 * @param id the unique identifier of the user to edit
 * @param name the new display name (max 30 characters)
 * @param email the new email address (max 150 characters)
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 */
public record UserEditRequest(long id, @NotBlank @Size(max = 30) String name, @NotBlank @Size(max = 150) String email) {
}
