package com.swp391.koibe.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
public record UserLoginDTO (
    @JsonProperty("email")
    @Email(message = "Email is not valid") @NotBlank(message = "Email is required") String email,

    @JsonProperty("password")
    @NotBlank(message = "Password is required") String password
){}
