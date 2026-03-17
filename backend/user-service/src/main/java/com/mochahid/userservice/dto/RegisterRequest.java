package com.mochahid.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "username is required")
    @Size(min = 3, max = 50, message = "username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "email is required")
    @Email(message = "invalid email format")
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 6, message = "password must be at least 6 characters")
    private String password;
}
