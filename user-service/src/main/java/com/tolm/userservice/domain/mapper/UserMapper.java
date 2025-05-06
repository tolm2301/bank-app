package com.tolm.userservice.domain.mapper;

import com.tolm.userservice.domain.dto.UserResponse;
import com.tolm.userservice.domain.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse mapToDto(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.isEnabled(),
                user.getRoles(),
                user.getCreateAt(),
                user.getUpdatedAt()
        );
    }
}
