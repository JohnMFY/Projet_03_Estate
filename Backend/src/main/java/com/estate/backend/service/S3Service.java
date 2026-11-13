package com.estate.backend.service;

// AWS S3
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

// Spring
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

// Utils
import java.util.UUID;

import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.time.Duration;

@Service
public class S3Service {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${aws.s3.bucket}")
    private String bucket;

    public S3Service(
    S3Client s3Client,
    S3Presigner s3Presigner
    ) {
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
    }

    public String uploadFile(MultipartFile file) {

        try {

            String fileName =
                UUID.randomUUID() + "-" + file.getOriginalFilename();

            PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(fileName)
                .contentType(file.getContentType())
                .build();

            s3Client.putObject(
                request,
                RequestBody.fromInputStream(
                    file.getInputStream(),
                    file.getSize()
                )
            );

            return fileName;

        } catch (Exception exception) {
            throw new RuntimeException(
                "Erreur lors de l'envoi de l'image",
                exception
            );
        }
    }
    public String getFileUrl(String fileName) {

        GetObjectRequest objectRequest = GetObjectRequest.builder()
            .bucket(bucket)
            .key(fileName)
            .build();

        GetObjectPresignRequest presignRequest =
            GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofHours(1))
                .getObjectRequest(objectRequest)
                .build();

        return s3Presigner
            .presignGetObject(presignRequest)
            .url()
            .toString();
    }
}