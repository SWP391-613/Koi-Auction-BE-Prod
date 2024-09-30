package com.swp391.koibe.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import lombok.Builder;

@Builder
public record UserDTO(
    @JsonProperty("id") Long id,

    @JsonProperty("full_name") String fullName,

    @JsonProperty("phone_number") String phoneNumber,

    @JsonProperty("emails")
    @Email(message = "Email should be valid") String email,

    @JsonProperty("address") String address,

    @JsonProperty("password")
    @NotBlank(message = "Password cannot be blank") String password,

    @JsonProperty("status_name") String statusName,

    @JsonProperty("date_of_birth") Date dob,

    @JsonProperty("avatar_url") String avatarUrl,

    @JsonProperty("google_account_id") int googleAccountId,

    @JsonProperty("role_name")
    @NotNull(message = "Role name is required") String roleName,

    @JsonProperty("account_balance") long accountBalance
) {}
