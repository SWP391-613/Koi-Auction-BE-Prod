package com.swp391.koibe.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import lombok.Builder;

@Builder
public record UserRegisterDTO(

    @JsonProperty("first_name")
    @NotBlank(message = "First name is required") String firstName,

    @JsonProperty("last_name")
    @NotBlank(message = "Last name is required") String lastName,

    @JsonProperty("email")
    @Email(message = "Email is invalid") String email,

    @JsonProperty("is_active")
    int isActive,

    @JsonProperty("is_subscription")
    Integer isSubscription,

    @JsonProperty("address")
    String address,

    @JsonProperty("password")
    @NotBlank(message = "Password is required") String password,

    @JsonProperty("confirm_password")
    @NotBlank(message = "Confirm password is required") String confirmPassword,

    @JsonProperty("date_of_birth")
    Date dateOfBirth,

    @JsonProperty("avatar_url")
    String avatarUrl,

    @JsonProperty("google_account_id")
    int googleAccountId,

    @JsonProperty("role_id")
    @NotNull(message = "Role ID is required") Long roleId

){}