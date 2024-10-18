package com.swp391.koibe.services.feedback;

import com.swp391.koibe.dtos.FeedbackDTO;
import com.swp391.koibe.exceptions.base.DataAlreadyExistException;
import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.Feedback;
import com.swp391.koibe.models.Order;
import com.swp391.koibe.models.User;
import com.swp391.koibe.repositories.FeedBackRepository;
import com.swp391.koibe.repositories.OrderRepository;
import com.swp391.koibe.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService implements IFeedbackService {
    private final FeedBackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Override
    public Feedback createFeedback(FeedbackDTO feedbackDTO) {

        if (feedbackRepository.existsByUserIdAndOrderId(feedbackDTO.getUserId(), feedbackDTO.getOrderId())) {
            throw new DataAlreadyExistException("Feedback already exists");
        }
        User user = userRepository.findById(feedbackDTO.getUserId()).orElseThrow(()
                -> new DataNotFoundException("User not found: " + feedbackDTO.getUserId()));
        Order order = orderRepository.findById(feedbackDTO.getOrderId()).orElseThrow(()
                -> new DataNotFoundException("Order not found: " + feedbackDTO.getOrderId()));

        if (order.getUser().getId() != user.getId()) {
            throw new DataNotFoundException("User not found in order");
        }

        Feedback feedback = Feedback.builder()
                .content(feedbackDTO.getContent())
                .rating(feedbackDTO.getRating())
                .user(user)
                .order(order)
                .createdAt(LocalDateTime.now())
                .build();
        return feedbackRepository.save(feedback);
    }

    @Override
    public List<Feedback> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        if (feedbacks.isEmpty()) {
            throw new DataNotFoundException("Feedback not found");
        }
        return feedbacks;
    }

    @Override
    public Feedback updateFeedback(long id, FeedbackDTO feedbackDTO) {
        Feedback feedback = feedbackRepository.findById(id).orElseThrow(()
                -> new DataNotFoundException("Feedback not found"));
        User existUser = userRepository.findById(feedbackDTO.getUserId()).orElseThrow(()
                -> new DataNotFoundException("User not found"));
        Order existOrder = orderRepository.findById(feedbackDTO.getUserId()).orElseThrow(()
                -> new DataNotFoundException("Order not found"));
        feedback.setContent(feedbackDTO.getContent());
        feedback.setRating(feedbackDTO.getRating());
        feedback.setUser(existUser);
        feedback.setOrder(existOrder);

        return feedbackRepository.save(feedback);
    }

    @Override
    public void deleteFeedback(long id) {
        Feedback feedback = feedbackRepository.findById(id).orElseThrow(()
                -> new DataNotFoundException("Feedback not found"));
        feedbackRepository.delete(feedback);
    }

    @Override
    public Feedback getFeedbackById(long id) {
        Feedback feedback = feedbackRepository.findById(id).orElseThrow(()
                -> new DataNotFoundException("Feedback not found"));
        return feedback;
    }
}
