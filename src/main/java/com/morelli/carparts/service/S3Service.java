package com.morelli.carparts.service;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.morelli.carparts.model.entity.FileMetadata;
import com.morelli.carparts.repository.FileMetadataRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class S3Service {

    private AmazonS3 amazonS3;
    private FileMetadataRepository fileMetadataRepository;

    private final String bucketName = "morelli-bucket";

    private void ensureBucketExists() {
        if (!amazonS3.doesBucketExistV2(bucketName)) {
            amazonS3.createBucket(bucketName);
        }
    }

    public String getFileUrl(String keyName) {
        ensureBucketExists();
        if (!amazonS3.doesObjectExist(bucketName, keyName)) {
            throw new RuntimeException("File does not exist: " + keyName);
        }
        return amazonS3.getUrl(bucketName, keyName).toString();
    }

    @Transactional
    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        File localFile = new File(System.getProperty("java.io.tmpdir") + "/" + Objects.requireNonNull(fileName));
        file.transferTo(localFile);

        ensureBucketExists();
        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, localFile));
        localFile.delete();

        String s3Url = amazonS3.getUrl(bucketName, fileName).toString();
        FileMetadata fileMetadata = FileMetadata.builder()
                .fileName(fileName)
                .s3Url(s3Url)
                .build();
        fileMetadataRepository.save(fileMetadata);

        return s3Url;
    }

    @Transactional
    public String deleteFile(String keyName) {
        ensureBucketExists();

        if (!amazonS3.doesObjectExist(bucketName, keyName)) {
            throw new RuntimeException("File does not exist: " + keyName);
        }

        amazonS3.deleteObject(bucketName, keyName);
        fileMetadataRepository.deleteByFileName(keyName);

        return "File deleted successfully: " + keyName;
    }
}


