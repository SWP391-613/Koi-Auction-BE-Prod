package com.swp391.koibe.controllers;

import com.swp391.koibe.components.LocalizationUtils;
import com.swp391.koibe.dtos.FeedbackDTO;
import com.swp391.koibe.enums.OrderStatus;
import com.swp391.koibe.exceptions.FeedbackResponseException;
import com.swp391.koibe.exceptions.MethodArgumentNotValidException;
import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.Feedback;
import com.swp391.koibe.models.Order;
import com.swp391.koibe.responses.FeedbackResponse;
import com.swp391.koibe.responses.base.BaseResponse;
import com.swp391.koibe.services.feedback.IFeedbackService;
import com.swp391.koibe.services.order.IOrderService;
import com.swp391.koibe.services.order.OrderService;
import com.swp391.koibe.utils.DTOConverter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/feedbacks")
@RequiredArgsConstructor
@Slf4j
public class FeedbackController {

    private final IFeedbackService feedbackService;
    private final LocalizationUtils localizationUtils;
    private final IOrderService orderService;

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_STAFF')")
    public ResponseEntity<List<FeedbackResponse>> getAllFeedbacks() {
        try {
            List<FeedbackResponse> response = feedbackService.getAllFeedbacks().stream()
                    .map(DTOConverter::fromFeedback)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new FeedbackResponseException(
                    localizationUtils.getLocalizedMessage("feedback.get_all_failed"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackResponse> getFeedbackById(@PathVariable long id) {
        try {
            FeedbackResponse response = DTOConverter.fromFeedback(feedbackService.getFeedbackById(id));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new FeedbackResponseException(
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
                throw new FeedbackResponseException("Feedback has already been submitted for this order.");
            }
            FeedbackResponse response = DTOConverter.fromFeedback(feedbackService.createFeedback(feedback));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalStateException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new FeedbackResponseException(
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
            FeedbackResponse response = DTOConverter.fromFeedback(feedbackService.createFeedback(feedback));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new FeedbackResponseException(
                    localizationUtils.getLocalizedMessage("feedback.update_failed"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFeedback(@PathVariable long id) {
        try {
            feedbackService.deleteFeedback(id);
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setMessage(localizationUtils.getLocalizedMessage("feedback.delete_successfully", id));
            return ResponseEntity.ok(baseResponse);
        } catch (Exception e) {
            throw new FeedbackResponseException(
                    localizationUtils.getLocalizedMessage("feedback.delete_failed"));
        }
    }

    @GetMapping("/order/{order_id}")
    public ResponseEntity<FeedbackResponse> getFeedbackByOrderId(@PathVariable long order_id) {
        try {
            Feedback feedback = feedbackService.getFeedbackByOrderId(order_id);
            if (feedback == null) {
                return ResponseEntity.notFound().build();
            }
            FeedbackResponse response = DTOConverter.fromFeedback(feedback);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new FeedbackResponseException(
                    localizationUtils.getLocalizedMessage("feedback.get_by_order_id_failed", order_id));
        }
    }
}
