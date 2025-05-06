package com.tolm.userservice.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request body for registering a new user") // DTO level description
public record UserRegistrationRequest(
        @Schema(description = "Unique username for the user", example = "user.name", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Username cannot be blank")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        String username,

        @Schema(description = "Unique email address for the user", example = "email@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        @Size(max = 100, message = "Email cannot exceed 100 characters")
        String email,

        @Schema(description = "User's password (will be hashed)", example = "str0ngp@assw0rd", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, message = "Password must be at least 8 characters")
        String password,

        @Schema(description = "User's first name", example = "firstname")
        @Size(max = 50, message = "First name cannot exceed 50 characters")
        String firstName,

        @Schema(description = "User's last name", example = "lastname")
        @Size(max = 50, message = "Last name cannot exceed 50 characters")
        String lastName) {
}
