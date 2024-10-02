package com.swp391.koibe.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import lombok.Builder

@Builder
@JvmRecord
data class UserLoginDTO(
    @field:JsonProperty("email") @param:JsonProperty("email") val email: @Email(message = "Email is not valid") @NotBlank(
        message = "Email is required"
    ) String?,

    @field:JsonProperty("password") @param:JsonProperty("password") val password: @NotBlank(message = "Password is required") String?
)
