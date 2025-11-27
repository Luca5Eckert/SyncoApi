package com.api.synco.module.authentication.application.dto.login;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginRequest(
        @NotBlank @Size(max = 150) String email,
        @NotBlank @Size(max = 180) String password) {
}
