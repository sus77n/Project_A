package com.example.project_a.controller;

import com.example.project_a.model.Media;
import com.example.project_a.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/media")
public class MediaController {
    @Autowired
    private MediaService mediaService;

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<Map<String, String>> uploadMedia(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = mediaService.store(file);
            String filePath = mediaService.getFileUrl(fileName);

            Map<String, String> response = new HashMap<>();
            response.put("fileName", fileName);
            response.put("filePath", filePath);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to upload file: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMedia(@RequestParam("fileName") String fileName) {
        return ResponseEntity.ok().body(Map.of("message", "File deleted successfully."));
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> getMediaByPath(@PathVariable Integer id) {
        Media media = mediaService.findMediaById(id);

        if (media == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, String> response = new HashMap<>();
        response.put("filePath", media.getFilePath());
        response.put("fileName", mediaService.getFileName(media.getFilePath()));

        return ResponseEntity.ok(response); // Return the stored image URL
    }
}
