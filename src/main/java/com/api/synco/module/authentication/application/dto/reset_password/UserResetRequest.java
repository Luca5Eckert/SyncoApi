package com.api.synco.module.authentication.application.dto.reset_password;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for password reset requests.
 *
 * <p>This record encapsulates the data required for changing a user's password,
 * including the current password for verification.</p>
 *
 * @param passwordActual the user's current password (max 180 characters)
 * @param newPassword the desired new password (max 180 characters)
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 */
public record UserResetRequest(
        @NotBlank(message = "The actual password can't be blank") @Size(max = 180, message = "The password can't be more then 180 characters") String passwordActual,
        @NotBlank(message = "The new password can't be blank") @Size(max = 180, message = "The password can't be more then 180 characters") String newPassword
) {

}
