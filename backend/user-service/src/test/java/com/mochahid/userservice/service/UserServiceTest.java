package com.mochahid.userservice.service;

import com.mochahid.common.exception.BadRequestException;
import com.mochahid.common.exception.DuplicateResourceException;
import com.mochahid.common.exception.ResourceNotFoundException;
import com.mochahid.userservice.dto.LoginRequest;
import com.mochahid.userservice.dto.RegisterRequest;
import com.mochahid.userservice.dto.UserResponse;
import com.mochahid.userservice.entity.User;
import com.mochahid.userservice.mapper.UserMapper;
import com.mochahid.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserResponse userResponse;
    private RegisterRequest registerRequest;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .username("testuser")
                .email("test@test.com")
                .password("password123")
                .build();
        user.setId(1L);
        user.setCreatedAt(LocalDateTime.now());

        userResponse = UserResponse.builder()
                .id(1L)
                .username("testuser")
                .email("test@test.com")
                .createdAt(LocalDateTime.now())
                .build();

        registerRequest = RegisterRequest.builder()
                .username("testuser")
                .email("test@test.com")
                .password("password123")
                .build();
    }

    @Test
    void getAllUsers_shouldReturnAll() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.toResponseList(any())).thenReturn(List.of(userResponse));

        List<UserResponse> result = userService.getAllUsers();

        assertThat(result).hasSize(1);
    }

    @Test
    void getUserById_shouldReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toResponse(user)).thenReturn(userResponse);

        UserResponse result = userService.getUserById(1L);

        assertThat(result.getUsername()).isEqualTo("testuser");
    }

    @Test
    void getUserById_shouldThrowWhenNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void register_shouldCreateUser() {
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.existsByUsername(any())).thenReturn(false);
        when(userMapper.toEntity(registerRequest)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(userResponse);

        UserResponse result = userService.register(registerRequest);

        assertThat(result.getUsername()).isEqualTo("testuser");
        verify(userRepository).save(user);
    }

    @Test
    void register_shouldThrowOnDuplicateEmail() {
        when(userRepository.existsByEmail("test@test.com")).thenReturn(true);

        assertThatThrownBy(() -> userService.register(registerRequest))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("email already registered");
    }

    @Test
    void register_shouldThrowOnDuplicateUsername() {
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        assertThatThrownBy(() -> userService.register(registerRequest))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("username already taken");
    }

    @Test
    void login_shouldReturnUserOnSuccess() {
        LoginRequest loginRequest = LoginRequest.builder()
                .email("test@test.com")
                .password("password123")
                .build();

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        when(userMapper.toResponse(user)).thenReturn(userResponse);

        UserResponse result = userService.login(loginRequest);

        assertThat(result.getEmail()).isEqualTo("test@test.com");
    }

    @Test
    void login_shouldThrowOnWrongPassword() {
        LoginRequest loginRequest = LoginRequest.builder()
                .email("test@test.com")
                .password("wrongpassword")
                .build();

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> userService.login(loginRequest))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("invalid email or password");
    }

    @Test
    void login_shouldThrowOnWrongEmail() {
        LoginRequest loginRequest = LoginRequest.builder()
                .email("wrong@test.com")
                .password("password123")
                .build();

        when(userRepository.findByEmail("wrong@test.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.login(loginRequest))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("invalid email or password");
    }

    @Test
    void deleteUser_shouldDeleteWhenExists() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    void deleteUser_shouldThrowWhenNotFound() {
        when(userRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> userService.deleteUser(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
