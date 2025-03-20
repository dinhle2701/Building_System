//package com.microservice.building_be.service;
//
//import com.microservice.building_be.dto.request.EmailRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class EmailService {
//
//    @Autowired
//    private JavaMailSender javaMailSender;
//
//
//    public EmailRequest sendEmailToUsers(EmailRequest emailRequest) {
//        // Lấy danh sách email từ đối tượng emailRequest
//        List<String> emailAddresses = emailRequest.getTo();
//        String subject = emailRequest.getSubject();
//        String text = emailRequest.getText();
//
//        // Duyệt qua danh sách email và gửi từng email
//        for (String email : emailAddresses) {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setTo(email);
//            message.setSubject(subject);
//            message.setText(text);
//            javaMailSender.send(message);
//        }
//        return emailRequest;
//    }
//
//}
//
