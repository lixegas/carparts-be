package com.morelli.carparts.service;


import com.morelli.carparts.model.entity.FileMetadata;
import com.morelli.carparts.repository.FileMetadataRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.transaction.annotation.Transactional;
@Service
@AllArgsConstructor
public class S3Service {

    private final S3Client s3Client;
    private final FileMetadataRepository fileMetadataRepository;

    private final String bucketName = "morelli-bucket";

    @Transactional
    public String uploadFile(MultipartFile file) {
        try {
            String keyName = file.getOriginalFilename();

            if (fileMetadataRepository.existsByFileName(keyName)) {
                throw new RuntimeException("File already exists: " + keyName);
            }

            File localFile = Files.createTempFile(keyName, null).toFile();
            file.transferTo(localFile);

            if (!s3Client.listBuckets().buckets().stream().anyMatch(b -> b.name().equals(bucketName))) {
                s3Client.createBucket(b -> b.bucket(bucketName));
            }

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .build();

            s3Client.putObject(putObjectRequest, localFile.toPath());
            String s3Url = s3Client.utilities().getUrl(b -> b.bucket(bucketName).key(keyName)).toString();

            FileMetadata fileMetadata = FileMetadata.builder()
                    .fileName(keyName)
                    .s3Url(s3Url)
                    .build();
            fileMetadataRepository.save(fileMetadata);

            return s3Url;

        } catch (IOException e) {
            throw new RuntimeException("File upload failed: " + e.getMessage(), e);
        }
    }

    @Transactional
    public String deleteFile(String fileName) {
        try {
            if (!fileMetadataRepository.existsByFileName(fileName)) {
                throw new RuntimeException("File does not exist: " + fileName);
            }

            s3Client.deleteObject(b -> b.bucket(bucketName).key(fileName));
            fileMetadataRepository.deleteByFileName(fileName);

            return "File deleted successfully: " + fileName;

        } catch (RuntimeException e) {
            throw new RuntimeException("File deletion failed: " + e.getMessage(), e);
        }
    }
}
