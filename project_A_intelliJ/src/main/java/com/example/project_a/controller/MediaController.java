package com.example.project_a.controller;

import com.example.project_a.model.Media;
import com.example.project_a.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/media")
public class MediaController {
    @Autowired
    private MediaService mediaService;

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<Map<String, String>> uploadMedia(@RequestParam("file") MultipartFile file,
                                                           @RequestParam("mediaAlt") String mediaAlt,
                                                           @RequestParam("mediaType") String mediaType) {
        try {
            String fileName = mediaService.store(file);

            Media media = new Media();
            media.setType(mediaType);
            media.setAlt(mediaAlt);
            media.setImageURL(mediaService.getFileUrl(fileName));
            mediaService.save(media);

            Media thumbnail = mediaService.findMediaById(media.getId());

            Map<String, String> response = new HashMap<>();
            response.put("mediaId", String.valueOf(media.getId()));
            response.put("fileName", fileName);
            response.put("thumbnail", thumbnail.getType());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to upload file: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMedia(@RequestParam("fileName") String fileName) {
        try {
            boolean deleted = mediaService.removeFromStorage(fileName); // Implement this in your StorageService
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
