package com.microservice.building_be.service.serviceimpl;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;

public class S3Uploader {

    private static final String BUCKET_NAME = "building-management-store-img";
    private static final String REGION = "us-east-1";

    private AmazonS3 s3Client;

    public S3Uploader(String accessKey, String secretKey) {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(REGION)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

    public String uploadFileToS3(File file, String fileName) {
        String fileUrl = "https://" + BUCKET_NAME + ".s3." + REGION + ".amazonaws.com/" + fileName;

        // Upload file lên S3
        s3Client.putObject(new PutObjectRequest(BUCKET_NAME, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return fileUrl;  // Trả về URL của file
    }

}
