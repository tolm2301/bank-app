package com.tolm.userservice.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthResponse(
        @Schema(description = "JWT Access Token")
        String accessToken
) {
}
