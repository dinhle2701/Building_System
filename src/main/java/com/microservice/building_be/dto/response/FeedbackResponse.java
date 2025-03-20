package com.microservice.building_be.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackResponse {
    private Long id;
    private String title;
    private String description;
    private String feedbackStatus;
    private String feedbackImg;
    private Timestamp createDate;
    private Long apartment_id;
    private String apartmentName;
}
