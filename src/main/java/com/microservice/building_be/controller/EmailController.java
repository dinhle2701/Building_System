//package com.microservice.building_be.controller;
//
//import com.microservice.building_be.dto.request.EmailRequest;
//import com.microservice.building_be.service.EmailService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/mail")
//@Slf4j
//public class EmailController {
//
//    @Autowired
//    private EmailService emailService;
//
//    // api send email
//    @PostMapping("")
//    public ResponseEntity<?> sendEmail(@RequestBody EmailRequest emailRequest){
//        try {
//            emailService.sendEmailToUsers(emailRequest);
//            log.info("Send email successfully!" + emailRequest);
//            return new ResponseEntity<>(emailRequest, HttpStatus.OK);
//        } catch (RuntimeException e){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//}
