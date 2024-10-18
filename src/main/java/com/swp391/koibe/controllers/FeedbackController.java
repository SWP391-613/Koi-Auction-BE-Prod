package com.swp391.koibe.controllers;

import com.swp391.koibe.components.LocalizationUtils;
import com.swp391.koibe.dtos.FeedbackDTO;
import com.swp391.koibe.exceptions.MethodArgumentNotValidException;
import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.responses.FeedbackResponse;
import com.swp391.koibe.services.feedback.IFeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/feedbacks")
@RequiredArgsConstructor
@Slf4j
public class FeedbackController {

    private final IFeedbackService feedbackService;
    private final LocalizationUtils localizationUtils;

    @GetMapping("")
    public ResponseEntity<FeedbackResponse> getAllFeedbacks() {
        try {
            FeedbackResponse response = FeedbackResponse.builder()
                    .message(localizationUtils.getLocalizedMessage("feedback.get_all_successfully"))
                    .listData(feedbackService.getAllFeedbacks())
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new DataNotFoundException(
                    localizationUtils.getLocalizedMessage("feedback.get_all_failed"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackResponse> getFeedbackById(@PathVariable long id) {
        try {
            FeedbackResponse response = FeedbackResponse.builder()
                    .message(localizationUtils.getLocalizedMessage("feedback.get_by_id_successfully"))
                    .singleData(feedbackService.getFeedbackById(id))
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new DataNotFoundException(
                    localizationUtils.getLocalizedMessage("feedback.get_by_id_failed", id));
        }
    }

    @PostMapping("")
    public ResponseEntity<FeedbackResponse> createFeedback(
            @Valid @RequestBody FeedbackDTO feedback,
            BindingResult result) {
        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(result);
        }
        try {
            FeedbackResponse response = FeedbackResponse.builder()
                    .message(localizationUtils.getLocalizedMessage("feedback.create_successfully"))
                    .singleData(feedbackService.createFeedback(feedback))
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            throw new RuntimeException(
                    localizationUtils.getLocalizedMessage("feedback.create_failed"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeedbackResponse> updateFeedback(
            @Valid @RequestBody FeedbackDTO feedback,
            @PathVariable long id,
            BindingResult result) {
        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(result);
        }
        try {
            FeedbackResponse response = FeedbackResponse.builder()
                    .message(localizationUtils.getLocalizedMessage("feedback.update_successfully"))
                    .singleData(feedbackService.updateFeedback(id, feedback))
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException(
                    localizationUtils.getLocalizedMessage("feedback.update_failed"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FeedbackResponse> deleteFeedback(@PathVariable long id) {
        try {
            feedbackService.deleteFeedback(id);
            FeedbackResponse response = FeedbackResponse.builder()
                    .message(localizationUtils.getLocalizedMessage("feedback.delete_successfully", id))
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException(
                    localizationUtils.getLocalizedMessage("feedback.delete_failed"));
        }
    }
}
