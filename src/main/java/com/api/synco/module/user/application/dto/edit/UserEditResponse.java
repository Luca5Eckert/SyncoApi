package com.api.synco.module.user.application.dto.edit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for user edit responses.
 *
 * <p>This record contains the updated user data returned after
 * successfully editing a user's profile.</p>
 *
 * @param id the unique identifier of the edited user
 * @param name the user's updated display name
 * @param email the user's updated email address
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 */
public record UserEditResponse(long id, @NotBlank @Size(max = 30) String name, @NotBlank @Size(max = 150) String email) {
}
