package com.example.project_a.service;

import com.example.project_a.model.Media;
import com.example.project_a.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class MediaService {
    @Autowired
    private MediaRepository mediaRepository;

    private final Path storageLocation = Paths.get("src/main/resources/static/uploads/");

    public String store(MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        if (!Files.exists(storageLocation)) {
            Files.createDirectories(storageLocation);
        }

        try {
            Files.copy(file.getInputStream(), this.storageLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new Exception("Failed to store file " + fileName, e);
        }
    }

    public String getFileUrl(String fileName) {
        return "/uploads/" + fileName;
    }

    public boolean removeFromStorage(String fileName) throws Exception {
        try {
            Path filePath = storageLocation.resolve(fileName);

            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new Exception("Failed to delete file " + fileName, e);
        }
    }

    public Media findMediaById(int id) {
        Media media = this.mediaRepository.getReferenceById(id);
        return media;
    }

    public void save(Media media) {
        this.mediaRepository.save(media);
    }
}
