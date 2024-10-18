package com.swp391.koibe.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDTO {
    @JsonProperty("content")
    private String content;

    @JsonProperty("rating")
    private int rating;

    @JsonProperty("user_id")
    @NotNull
    private long userId;

    @JsonProperty("order_id")
    @NotNull
    private long orderId;
}
