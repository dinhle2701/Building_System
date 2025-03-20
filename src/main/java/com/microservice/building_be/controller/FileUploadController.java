//package com.microservice.building_be.controller;
//
//import com.microservice.building_be.service.serviceimpl.DatabaseHandler;
//import com.microservice.building_be.service.serviceimpl.S3Uploader;
//import io.github.cdimascio.dotenv.Dotenv;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//
//@RestController
//@RequestMapping("/api/v1/upload")
//public class FileUploadController {
//
//    private final S3Uploader s3Uploader;
//    private final DatabaseHandler databaseHandler;
//
//    private static final Dotenv dotenv = Dotenv.load();
//
//
//    public FileUploadController() {
//        String accessKey = dotenv.get("AWS_ACCESS_KEY_ID");
//        String secretKey = dotenv.get("AWS_SECRET_ACCESS_KEY");
//
//        this.s3Uploader = new S3Uploader(accessKey, secretKey);
//        this.databaseHandler = new DatabaseHandler();
//    }
//
//
//
//    @PostMapping("/{staff_id}")
//    public String uploadStaffFile(@RequestParam("file") MultipartFile file, @PathVariable("staff_id") int staff_id) {
//        try {
//            // Lưu file tạm thời
//            File tempFile = File.createTempFile("upload", file.getOriginalFilename());
//            file.transferTo(tempFile);
//
//            // Upload file lên S3
//            String fileUrl = s3Uploader.uploadFileToS3(tempFile, file.getOriginalFilename());
//
//            // Lưu URL vào cơ sở dữ liệu
//            databaseHandler.saveStaffImageUrlToDB(staff_id, fileUrl);
//
//            return "File uploaded successfully: " + fileUrl;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "File upload failed!";
//        }
//    }
//}
