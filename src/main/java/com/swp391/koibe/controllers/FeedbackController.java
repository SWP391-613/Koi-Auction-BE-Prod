package com.swp391.koibe.controllers;

import com.swp391.koibe.components.LocalizationUtils;
import com.swp391.koibe.dtos.FeedbackDTO;
import com.swp391.koibe.enums.OrderStatus;
import com.swp391.koibe.exceptions.MethodArgumentNotValidException;
import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.Feedback;
import com.swp391.koibe.models.Order;
import com.swp391.koibe.responses.FeedbackResponse;
import com.swp391.koibe.services.feedback.IFeedbackService;
import com.swp391.koibe.services.order.IOrderService;
import com.swp391.koibe.services.order.OrderService;
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
    private final IOrderService orderService;

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
            // Check if the order is in DELIVERED status
            Order order = orderService.getOrder(feedback.getOrderId());
            if (order.getStatus() != OrderStatus.DELIVERED) {
                throw new IllegalStateException("Feedback can only be submitted for delivered orders.");
            }

            // Check if feedback already exists for this order
            if (feedbackService.getFeedbackByOrderId(feedback.getOrderId()) != null) {
                throw new IllegalStateException("Feedback has already been submitted for this order.");
            }

            FeedbackResponse response = FeedbackResponse.builder()
                    .message(localizationUtils.getLocalizedMessage("feedback.create_successfully"))
                    .singleData(feedbackService.createFeedback(feedback))
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalStateException e) {
            throw new RuntimeException(e.getMessage());
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

    @GetMapping("/order/{order_id}")
    public ResponseEntity<FeedbackResponse> getFeedbackByOrderId(@PathVariable long order_id) {
        try {
            Feedback feedback = feedbackService.getFeedbackByOrderId(order_id);
            FeedbackResponse response = FeedbackResponse.builder()
                    .message(feedback != null
                            ? localizationUtils.getLocalizedMessage("feedback.get_by_order_id_successfully")
                            : localizationUtils.getLocalizedMessage("feedback.not_found_for_order"))
                    .singleData(feedback)
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    FeedbackResponse.builder()
                            .message(localizationUtils.getLocalizedMessage("feedback.get_by_order_id_failed", order_id))
                            .build());
        }
    }
}
