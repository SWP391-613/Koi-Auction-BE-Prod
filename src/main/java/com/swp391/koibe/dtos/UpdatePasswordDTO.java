package com.swp391.koibe.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePasswordDTO(
    @JsonProperty("email")
    @Email(message = "Email must be a valid email")
    @NotBlank(message = "Email must not be blank")
    String email,

    @JsonProperty("new_password")
    @Size(min = 8, max=200, message = "New password must be at least 8 characters long and at most 200 characters long")
    @NotBlank(message = "New password must not be blank")
    String newPassword
) {}
