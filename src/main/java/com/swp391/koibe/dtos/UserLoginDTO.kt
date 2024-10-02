package com.swp391.koibe.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class UserLoginDTO(
    @JsonProperty("email")
    @Email(message = "Email is not valid")
    @NotBlank(message = "Email is required")
    val email: String,

    @JsonProperty("password")
    @NotBlank(message = "Password is required")
    val password: String
)
