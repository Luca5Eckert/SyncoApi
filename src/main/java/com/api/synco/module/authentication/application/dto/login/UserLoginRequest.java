package com.api.synco.module.authentication.application.dto.login;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for user login requests.
 *
 * <p>This record encapsulates the credentials required for user authentication.</p>
 *
 * @param email the user's email address (max 150 characters)
 * @param password the user's password (max 180 characters)
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 */
public record UserLoginRequest(
        @NotBlank @Size(max = 150) String email,
        @NotBlank @Size(max = 180) String password) {
}
