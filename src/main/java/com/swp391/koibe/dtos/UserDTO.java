package com.swp391.koibe.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Date;
import lombok.Builder;

@Builder
public record UserDTO(
    @JsonProperty("first_name") String firstName,

    @JsonProperty("last_name") String lastName,

    @JsonProperty("phone_number") String phoneNumber,

    @JsonProperty("email")
    @Email(message = "Email should be valid") String email,

    @JsonProperty("address") String address,

    @JsonProperty("password") String password,

    @JsonProperty("is_active") boolean isActive,

    @JsonProperty("is_subscription") boolean isSubscription,

    @Pattern(regexp = "UNVERIFIED|VERIFIED|BANNED", message = "Status must be either UNVERIFIED, VERIFIED, BANNED")
    @JsonProperty("status_name") String statusName,

    @JsonProperty("date_of_birth") String dob,

    @JsonProperty("avatar_url") String avatarUrl,

    @JsonProperty("google_account_id") int googleAccountId,

    @JsonProperty("account_balance") long accountBalance
) {}
