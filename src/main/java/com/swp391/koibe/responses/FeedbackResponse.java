package com.swp391.koibe.responses;

import com.swp391.koibe.models.Feedback;
import com.swp391.koibe.responses.base.BaseResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@NoArgsConstructor
public class FeedbackResponse extends BaseResponse<Feedback> {

}
