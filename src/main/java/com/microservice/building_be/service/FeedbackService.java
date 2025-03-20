package com.microservice.building_be.service;

import com.microservice.building_be.dto.request.FeedbackRequest;
import com.microservice.building_be.dto.response.FeedbackResponse;
import com.microservice.building_be.model.Feedback;
import com.microservice.building_be.model.Resident;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FeedbackService {
    Page<FeedbackResponse> getAllFeedbacks(int page, int size);

    Page<FeedbackResponse> getAllFeedbackByApartmentName(int page, int size, String apartment_name);

    FeedbackResponse getFeedbackById(Long id);

    List<Feedback> getFeedbackByApartmentName(String apartment_name);

    Feedback createFeedback(Long apartment_id,  FeedbackRequest feedback);

    Feedback updateStatusFeedback(Long feedback_id);

}
