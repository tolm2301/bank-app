package com.tolm.userservice.domain.service;

import com.tolm.userservice.domain.dto.UserRegistrationRequest;
import com.tolm.userservice.domain.dto.UserResponse;
import com.tolm.userservice.domain.entities.User;

import java.util.Optional;

public interface UserService {

    UserResponse registerUser(UserRegistrationRequest registrationRequest);
    Optional<UserResponse> findUserById(Long id);
    Optional<UserResponse> findUserByUsername(String username);

    /**
     * Need for spring security
     * @param username
     * @return
     */
    Optional<User> findUserEntityByUserName(String username);
}
