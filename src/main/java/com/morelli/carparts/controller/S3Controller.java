package com.morelli.carparts.controller;


import com.morelli.carparts.service.S3Service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


@RestController
@RequestMapping("/api/v1/s3")
@AllArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping("/")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = s3Service.uploadFile(file);
            return ResponseEntity.ok("File uploaded successfully. URL: " + fileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("File upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<String> getFile(@PathVariable String fileName) {
        try {
            String fileUrl = s3Service.getFileUrl(fileName);
            return ResponseEntity.ok(fileUrl);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("File not found: " + e.getMessage());
        }
    }

    @DeleteMapping("/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        try {
            String deleteResponse = s3Service.deleteFile(fileName);
            return ResponseEntity.ok(deleteResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("File deletion failed: " + e.getMessage());
        }
    }
}


