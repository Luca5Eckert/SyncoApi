package com.api.synco.module.authentication.application.dto.register;

import com.api.synco.module.user.domain.enumerator.RoleUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for user registration responses.
 *
 * <p>This record contains the data returned after successfully registering
 * a new user account.</p>
 *
 * @param id the unique identifier assigned to the new user
 * @param name the user's display name
 * @param email the user's email address
 * @param roleUser the role assigned to the user (default is USER)
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 */
public record UserRegisterResponse( long id, @NotBlank @Size(max = 30) String name, @NotBlank @Size(max = 150) String email, @NotBlank RoleUser roleUser){
}
