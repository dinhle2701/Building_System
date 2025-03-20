package com.microservice.building_be.service.serviceimpl;

import com.microservice.building_be.dto.request.FeedbackRequest;
import com.microservice.building_be.dto.response.FeedbackResponse;
import com.microservice.building_be.exception.ResourceNotFoundException;
import com.microservice.building_be.model.Apartment;
import com.microservice.building_be.model.Feedback;
import com.microservice.building_be.model.Resident;
import com.microservice.building_be.repository.ApartmentRepository;
import com.microservice.building_be.repository.FeedbackRepository;
import com.microservice.building_be.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private ApartmentRepository apartmentRepository;


    @Override
    public Page<FeedbackResponse> getAllFeedbacks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createDate"));
        Page<Feedback> feedbackPage = feedbackRepository.findAll(pageable);
        // Chuyển đổi thành FeedbackResponse
        Page<FeedbackResponse> feedbackResponsePage = feedbackPage.map(feedback -> {
            FeedbackResponse response = new FeedbackResponse();
            response.setId(feedback.getId());
            response.setTitle(feedback.getTitle());
            response.setFeedbackStatus(feedback.getFeedbackStatus());
            response.setCreateDate(feedback.getCreateDate());
            response.setApartmentName(feedback.getApartment().getApartment_name());
            return response;
        });

        return feedbackResponsePage;
    }

    @Override
    public Page<FeedbackResponse> getAllFeedbackByApartmentName(int page, int size, String apartment_name) {
        Pageable pageable = PageRequest.of(page, size); // Tạo Pageable với số trang và kích thước trang

        // Truy vấn với phân trang từ repository (sử dụng method với Pageable)
        Page<Feedback> feedbackPage = feedbackRepository.findByApartmentNameNative(apartment_name, pageable);

        // Chuyển đổi từ Feedback sang FeedbackResponse DTO
        Page<FeedbackResponse> feedbackResponsePage = feedbackPage.map(feedback -> new FeedbackResponse(
                feedback.getId(),
                feedback.getTitle(),
                feedback.getDescription(),
                feedback.getFeedbackStatus(),
                feedback.getFeedbackImg(),
                feedback.getCreateDate(),
                feedback.getId(),
                feedback.getApartment().getApartment_name() // Lấy tên căn hộ từ quan hệ apartment
        ));

        return feedbackResponsePage;
    }


    @Override
    public FeedbackResponse getFeedbackById(Long id) {
        Feedback feedback = feedbackRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy feed back có id: " + id));
        FeedbackResponse feedbackResponse = new FeedbackResponse();
        feedbackResponse.setApartmentName(feedback.getApartment().getApartment_name());
        feedbackResponse.setTitle(feedback.getTitle());
        feedbackResponse.setDescription(feedback.getDescription());
        feedbackResponse.setFeedbackImg(feedback.getFeedbackImg());
        feedbackResponse.setFeedbackStatus(feedback.getFeedbackStatus());
        feedbackResponse.setCreateDate(feedback.getCreateDate());

        return feedbackResponse;
    }

    @Override
    public List<Feedback> getFeedbackByApartmentName(String apartment_name) {
        return feedbackRepository.findByApartment_Apartment_name(apartment_name);
    }

    @Override
    public Feedback createFeedback(Long apartment_id,  FeedbackRequest feedback) {
        Feedback newFeedback = new Feedback();

        Apartment apartment = apartmentRepository.findById(apartment_id).orElseThrow(() -> new RuntimeException());
        newFeedback.setTitle(feedback.getTitle());
        newFeedback.setFeedbackStatus("ĐANG CHỜ XEM XÉT");
        newFeedback.setDescription(feedback.getDescription());
        newFeedback.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        newFeedback.setApartment(apartment);

        return feedbackRepository.save(newFeedback);
    }

//    @PostMapping("")
//    public String uploadFile(@RequestParam("file") MultipartFile file) {
//        try {
//            // Lưu file tạm thời
//            File tempFile = File.createTempFile("upload", file.getOriginalFilename());
//            file.transferTo(tempFile);
//
//            // Upload file lên S3
//            String fileUrl = s3Uploader.uploadFileToS3(tempFile, file.getOriginalFilename());
//
//            // Lưu URL vào cơ sở dữ liệu
//            databaseHandler.saveImageUrlToDB(fileUrl);
//
//            return "File uploaded successfully: " + fileUrl;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "File upload failed!";
//        }
//    }

    @Override
    public Feedback updateStatusFeedback(Long feedback_id) {
        Feedback feedback = feedbackRepository.findById(feedback_id).orElseThrow(() -> new RuntimeException("Không tìm thấy feedback có id: " + feedback_id));
        feedback.setFeedbackStatus("ĐÃ XÉT DUYỆT");
        return feedbackRepository.save(feedback);
    }
}
