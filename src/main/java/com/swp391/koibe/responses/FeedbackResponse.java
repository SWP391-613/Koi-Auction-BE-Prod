package com.swp391.koibe.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swp391.koibe.models.Feedback;
import com.swp391.koibe.responses.base.BaseResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SuperBuilder
@NoArgsConstructor
public class FeedbackResponse {
    @JsonProperty("content")
    String content;

    @JsonProperty("rating")
    int rating;

    @JsonProperty("created_at")
    LocalDateTime createAt;

}
