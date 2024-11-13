package com.swp391.koibe.dtos.koi;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record KoiDTO(
    @JsonProperty("name")
    @NotBlank(message = "Koi name is required when create a new koi")
    String name,

    @NotNull(message = "Koi base_price is required when create a new koi")
    @Min(value = 1000000, message = "Minimum koi price is 1.000.000(vnd)")
    @Max(value = 30000000, message = "Maximum koi price is 30.000.000(vnd)")
    @JsonProperty("base_price") Long price,

    @NotBlank(message = "Koi thumbnail is required when create a new koi")
    @JsonProperty("thumbnail") String thumbnail,

    @NotBlank(message = "Koi gender is required when create a new koi")
    @Pattern(regexp = "MALE|FEMALE|UNKNOWN", message = "Gender must be either MALE | FEMALE | UNKNOWN")
    @JsonProperty("gender") String sex,

    @NotNull(message = "Koi length is required when create a new koi")
    @Min(value = 1, message = "Koi length must be greater than 0(cm)")
    @Max(value = 150, message = "Koi length must be less than 150(cm)")
    @JsonProperty("length") Integer length,

    @NotNull(message = "Koi year born is required when create a new koi")
    @Min(value = 1, message = "Koi year born must be greater than 0")
    @Max(value = 230, message = "Koi year born must be less than 230")
    @JsonProperty("year_born") Integer yearBorn,

    @JsonProperty("description") String description,

    @Positive(message = "Koi category id must be greater than 0")
    @NotNull(message = "Koi category id is required when create a new koi")
    @JsonProperty("category_id") Integer categoryId,

    @Positive(message = "Koi owner id must be greater than 0")
    @NotNull(message = "Koi owner id is required when create a new koi")
    @JsonProperty("owner_id") long ownerId
) {}
