package com.example.project_a.controller;

import com.example.project_a.model.Media;
import com.example.project_a.repository.MediaRepository;
import com.example.project_a.service.MediaService;
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
import java.util.UUID;

@Controller
@RequestMapping("/media")
public class MediaController {

    @Autowired
    private MediaRepository mediaRepository;

    private static final String UPLOAD_DIR = "project_A_intelliJ/src/main/resources/static/assets/uploads/";

    public MediaController() {}

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "alt", defaultValue = "") String alt
    ) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty.");
        }

        try {
            // Generate a unique file name
            String originalFileName = file.getOriginalFilename();
            String fileName = UUID.randomUUID() + "_" + originalFileName;

            // Ensure the upload directory exists
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Save the file
            Path filePath = uploadPath.resolve(fileName);
            Files.write(filePath, file.getBytes());

            // Save metadata in the database
            Media media = new Media();
            media.setName(originalFileName);
            media.setImageURL(UPLOAD_DIR + fileName);
            media.setAlt(alt);

            System.out.println("this is fileName: " + fileName);
            System.out.println("this is media: " + media.toString());
            mediaRepository.save(media);
            return ResponseEntity.ok(media);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while uploading the file.");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMedia(@PathVariable Integer id) {

        try {

            Media media = mediaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Media not found with id: " + id));

            // Delete the file from the filesystem or cloud storage
            Path filePath = Paths.get("uploads/" + media.getName());
            try {
                Files.delete(filePath);
            } catch (IOException e) {
                throw new RuntimeException("Error deleting file: " + e.getMessage());
            }

            mediaRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // HTTP 204 No Content
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
