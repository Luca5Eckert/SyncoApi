package com.api.synco.module.authentication.application.dto.register;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for user registration requests.
 *
 * <p>This record encapsulates all the data required for creating a new user account.</p>
 *
 * @param name the user's display name (max 30 characters)
 * @param email the user's email address (max 150 characters)
 * @param password the user's password (max 180 characters)
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 */
public record UserRegisterRequest(@NotBlank @Size(max = 30) String name, @NotBlank @Size(max = 150) String email, @NotBlank @Size(max = 180)  String password) {
}
