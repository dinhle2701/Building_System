package com.microservice.building_be.controller;

import com.microservice.building_be.dto.request.FeedbackRequest;
import com.microservice.building_be.dto.response.FeedbackResponse;
import com.microservice.building_be.exception.ResourceNotFoundException;
import com.microservice.building_be.model.Feedback;
import com.microservice.building_be.service.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.tool.schema.spi.SqlScriptException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/feedback")
@Slf4j
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("")
    public ResponseEntity<Page<FeedbackResponse>> getAllFeedback(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1000") int size
    ) {
        try {
            Page<FeedbackResponse> feedbacks = feedbackService.getAllFeedbacks(page, size);
            log.info("Get residents successfully");
            return new ResponseEntity<>(feedbacks, HttpStatus.OK);
        } catch (SqlScriptException e) {
            log.warn(String.valueOf(e));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/apartment_name/{apartment_name}")
    public ResponseEntity<Page<FeedbackResponse>> getAllFeedbackByApartmentName(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @PathVariable("apartment_name") String apartment_name
    ) {
        try {
            Page<FeedbackResponse> feedbacks = feedbackService.getAllFeedbackByApartmentName(page, size, apartment_name);
            log.info("Get feedback by apartment name successfully");
            return new ResponseEntity<>(feedbacks, HttpStatus.OK);
        } catch (SqlScriptException e) {
            log.warn(String.valueOf(e));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{feedback_id}")
    public ResponseEntity<FeedbackResponse> getFeedbackById(@PathVariable("feedback_id") Long id){
        try {
            FeedbackResponse feedback = feedbackService.getFeedbackById(id);
            return new ResponseEntity<>(feedback, HttpStatus.OK);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    @GetMapping("/apartment_name/{apartment_name}")
//    public ResponseEntity<List<Feedback>> getFeedbackByApartmentName(@PathVariable("apartment_name") String apartment_name){
//        try {
//            List<Feedback> feedbacks = feedbackService.getFeedbackByApartmentName(apartment_name);
//            return new ResponseEntity<>(feedbacks, HttpStatus.OK);
//        } catch (ResourceNotFoundException e){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

    @PostMapping("/create/{apartment_id}")
    public ResponseEntity<Feedback> createFeedback(
//            @PathVariable String apartment_name,
            @PathVariable("apartment_id") Long apartment_id,
            @RequestBody FeedbackRequest feedback) {
        try {
            Feedback createFeedback = feedbackService.createFeedback(apartment_id, feedback);
            return new ResponseEntity<>(createFeedback, HttpStatus.OK);
        } catch (SqlScriptException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Cập nhật trạng thái feedback theo ID
    @PutMapping("/update-status/{id}")
    public ResponseEntity<Feedback> updateFeedbackStatusById(
            @PathVariable Long id) {
        try {
            Feedback feedback = feedbackService.updateStatusFeedback(id);
            return new ResponseEntity<>(feedback, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
