package com.example.project_a.service;

import com.example.project_a.model.Media;
import com.example.project_a.repository.MediaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MediaService {
    @Autowired
    private MediaRepository mediaRepository;

    @Value("${uploads.location}")
    private Path storageLocation;

    public String store(MultipartFile file) throws Exception {
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = "";
        String fileBaseName = originalFileName;

        // Extract file extension
        int lastDotIndex = originalFileName.lastIndexOf(".");
        if (lastDotIndex > 0) {
            fileExtension = originalFileName.substring(lastDotIndex); // Get extension (e.g., .jpg)
            fileBaseName = originalFileName.substring(0, lastDotIndex); // Get filename without extension
        }

        // Pattern to check if filename already has a number at the end (e.g., "image(1).jpg")
        Pattern pattern = Pattern.compile("^(.*)\\((\\d+)\\)$");
        Matcher matcher = pattern.matcher(fileBaseName);

        int count = 1;
        if (matcher.matches()) {
            fileBaseName = matcher.group(1); // Get base name without number
            count = Integer.parseInt(matcher.group(2)) + 1; // Extract and increment existing number
        }

        Path targetPath = this.storageLocation.resolve(originalFileName);

        // Find the next available file name
        while (Files.exists(targetPath)) {
            String newFileName = String.format("%s(%d)%s", fileBaseName, count, fileExtension);
            targetPath = this.storageLocation.resolve(newFileName);
            count++;
        }

        // Ensure storage directory exists
        if (!Files.exists(storageLocation)) {
            Files.createDirectories(storageLocation);
        }

        try {
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            return targetPath.getFileName().toString();
        } catch (IOException e) {
            throw new Exception("Failed to store file " + originalFileName, e);
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

    public void removeByName(String fileName) {
        try {
            removeFromStorage(fileName);
            mediaRepository.deleteByFilePath(getFileUrl(fileName)); // Implement thi    s in your repository
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Media findMediaById(Integer id) {
        return mediaRepository.findById(id).orElse(null);
    }

    public void save(Media media) {
        this.mediaRepository.save(media);
    }

    public String getFileName(String path) {
        return Paths.get(path).getFileName().toString();
    }

    public Media constructMedia(String filePath, String alt, String type) {
        Media media = new Media();
        media.setFilePath(filePath);
        media.setAlt(alt);
        media.setType(type);
        return mediaRepository.save(media);
    }

    public void removeMediaById(Integer id) {
        this.mediaRepository.deleteById(id);
    }

    public List<String> getListOfMediaInJson(String sliderImages) {
        // Convert JSON string to List<String>
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> imagePaths = null;
        try {
            imagePaths = objectMapper.readValue(sliderImages, new TypeReference<List<String>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return imagePaths;
    }


}
