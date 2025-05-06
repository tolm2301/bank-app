package com.tolm.userservice.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @Schema(description = "Username", example = "testuser", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Username cannot be blank")
        String username,

        @Schema(description = "Password", example = "password123", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Password cannot be blank")
        String password
) {
}
