package com.example.project_a.controller;

import com.example.project_a.model.Media;
import com.example.project_a.model.Product;
import com.example.project_a.repository.MediaRepository;
import com.example.project_a.service.MediaService;
import com.example.project_a.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/media")
public class MediaController {
    @Autowired
    private MediaService mediaService;

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<Map<String, String>> uploadMedia(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = mediaService.store(file);
            Map<String, String> response = new HashMap<>();
            response.put("fileName", fileName);
            response.put("fileUrl", mediaService.getFileUrl(fileName)); // Optional
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to upload file: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMedia(@RequestParam("fileName") String fileName) {
        try {
            boolean deleted = mediaService.delete(fileName); // Implement this in your StorageService
            if (deleted) {
                return ResponseEntity.ok().body(Map.of("message", "File deleted successfully."));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "File not found."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to delete file: " + e.getMessage()));
        }
    }
}
