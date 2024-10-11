package com.swp391.koibe.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.Date;
import lombok.Builder;

@Builder
public record StaffRegisterDTO(

    @JsonProperty("first_name")
    @NotBlank(message = "First name is required") String firstName,

    @JsonProperty("last_name")
    @NotBlank(message = "Last name is required") String lastName,

    @NotBlank(message = "Email is required")
    @JsonProperty("email")
    @Email(message = "Email is invalid") String email,

    @NotBlank(message = "Phone number is required")
    @JsonProperty("phone_number")
    @Pattern(regexp = "^(\\+84|0)\\d{9,10}$", message = "Phone number is invalid") String phoneNumber,

    @JsonProperty("is_active")
    boolean isActive,

    @JsonProperty("is_subscription")
    boolean isSubscription,

    @JsonProperty("address")
    String address,

    @JsonProperty("password")
    @NotBlank(message = "Password is required") String password,

    @JsonProperty("date_of_birth")
    Date dateOfBirth,

    @JsonProperty("avatar_url")
    String avatarUrl,

    @JsonProperty("google_account_id")
    int googleAccountId
){}