package com.api.synco.module.user.application.dto.create;

import com.api.synco.module.user.domain.enumerator.RoleUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for user creation requests.
 *
 * <p>This record encapsulates all the data required to create a new user
 * in the system. All fields are validated using Jakarta Bean Validation.</p>
 *
 * @param name the user's display name (max 30 characters)
 * @param email the user's email address (max 150 characters)
 * @param password the user's password (max 180 characters)
 * @param roleUser the role to assign to the new user
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 */
public record UserCreateRequest(@NotBlank @Size(max = 30) String name, @NotBlank @Size(max = 150) String email, @NotBlank @Size(max = 180)  String password, @NotNull RoleUser roleUser) {

}
