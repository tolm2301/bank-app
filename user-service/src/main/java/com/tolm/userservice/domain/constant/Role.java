package com.tolm.userservice.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ROLE_USER("ROLE_USER");

    private final String name;
}
