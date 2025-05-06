package com.tolm.userservice.domain.service.impl;

import com.tolm.userservice.domain.constant.Role;
import com.tolm.userservice.domain.dto.UserRegistrationRequest;
import com.tolm.userservice.domain.dto.UserResponse;
import com.tolm.userservice.domain.entities.User;
import com.tolm.userservice.domain.exception.RegistrationException;
import com.tolm.userservice.domain.mapper.UserMapper;
import com.tolm.userservice.domain.repository.UserRepository;
import com.tolm.userservice.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserResponse registerUser(UserRegistrationRequest request) {

        // 1. Check if username or email already exists
        if(userRepository.existsByUsername(request.username())) {
            throw new RegistrationException("Username already exists: " + request.username());
        }

        if(userRepository.existsByEmail(request.email())) {
            throw new RegistrationException("Email already exists: " + request.email());
        }

        // 2. Create new User entity
        User newUser = new User(
                request.username(),
                request.email(),
                passwordEncoder.encode(request.password()), // Sure password was hashed
                request.firstName(),
                request.lastName());

        // 3. Save the user
        User saveUser = userRepository.save(newUser);

        // 4. Map entity to response DTO
        return userMapper.mapToDto(saveUser);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserResponse> findUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::mapToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserResponse> findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::mapToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserEntityByUserName(String username) {
        return userRepository.findByUsername(username);
    }
}
