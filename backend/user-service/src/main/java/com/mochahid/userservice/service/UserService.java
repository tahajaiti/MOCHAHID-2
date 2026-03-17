package com.mochahid.userservice.service;

import com.mochahid.common.exception.BadRequestException;
import com.mochahid.common.exception.DuplicateResourceException;
import com.mochahid.common.exception.ResourceNotFoundException;
import com.mochahid.userservice.dto.LoginRequest;
import com.mochahid.userservice.dto.RegisterRequest;
import com.mochahid.userservice.dto.UserResponse;
import com.mochahid.userservice.dto.UserUpdateRequest;
import com.mochahid.userservice.entity.User;
import com.mochahid.userservice.mapper.UserMapper;
import com.mochahid.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponse> getAllUsers() {
        return userMapper.toResponseList(userRepository.findAll());
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user", id));
        return userMapper.toResponse(user);
    }

    @Transactional
    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("email already registered");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("username already taken");
        }

        User user = userMapper.toEntity(request);
        return userMapper.toResponse(userRepository.save(user));
    }

    public UserResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("invalid email or password"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new BadRequestException("invalid email or password");
        }

        return userMapper.toResponse(user);
    }

    @Transactional
    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user", id));

        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new DuplicateResourceException("email already registered");
            }
        }

        if (request.getUsername() != null && !request.getUsername().equals(user.getUsername())) {
            if (userRepository.existsByUsername(request.getUsername())) {
                throw new DuplicateResourceException("username already taken");
            }
        }

        userMapper.updateEntity(request, user);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("user", id);
        }
        userRepository.deleteById(id);
    }
}
