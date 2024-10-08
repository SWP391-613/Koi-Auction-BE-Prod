package com.swp391.koibe.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record CategoryDTO(
    @NotEmpty(message = "Category name is required") String name
) {}
