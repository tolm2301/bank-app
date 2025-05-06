package com.tolm.userservice.domain.dto;

import com.tolm.userservice.domain.constant.Role;

import java.time.LocalDateTime;
import java.util.Set;

public record UserResponse(
        Long id,
        String username,
        String email,
        String firstName,
        String lastName,
        boolean enabled,
        Set<Role> roles,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
