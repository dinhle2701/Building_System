package com.microservice.building_be.dto.request;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackRequest {
    private String title;
    private String description;
    private String feedback_status;
    private String feedback_img;
}
