package com.morelli.carparts.controller;


import com.morelli.carparts.model.entity.FileMetadata;
import com.morelli.carparts.repository.FileMetadataRepository;
import com.morelli.carparts.service.S3Service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;


@RestController
@RequestMapping("/api/v1/storage/s3")
@AllArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping("/")
    // FUNZIONA
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String s3Url = s3Service.uploadFile(file);
            return ResponseEntity.ok("File uploaded successfully: " + s3Url);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{fileName}")
    // FUNZIONA
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        try {
            String response = s3Service.deleteFile(fileName);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}

