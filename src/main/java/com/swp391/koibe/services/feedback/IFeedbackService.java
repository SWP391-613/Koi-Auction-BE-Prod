package com.swp391.koibe.services.feedback;


import com.swp391.koibe.dtos.FeedbackDTO;
import com.swp391.koibe.models.Feedback;

import java.util.List;

public interface IFeedbackService {
    Feedback createFeedback(FeedbackDTO feedbackDTO);
    List<Feedback> getAllFeedbacks();
    Feedback updateFeedback(long id, FeedbackDTO feedbackDTO);
    void deleteFeedback(long id);
    Feedback getFeedbackById(long id);
    Feedback getFeedbackByOrderId(long orderId);
}
