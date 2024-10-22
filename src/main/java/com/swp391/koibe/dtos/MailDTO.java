package com.swp391.koibe.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record MailDTO(
   @NotBlank(message = "Email Title is required")
   String title,

   @JsonProperty("message")
   String message
) {}
