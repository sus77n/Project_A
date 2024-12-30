package com.example.project_a.service;

import com.example.project_a.model.Media;
import com.example.project_a.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MediaService {
    @Autowired
    private MediaRepository mediaRepository;

    public void save(Media media) {
        mediaRepository.save(media);
    }

    public Media findById(Integer id) {
        return mediaRepository.findById(id).orElse(null);
    }

    public void deleteById(Integer id) {
        if (findById(id) == null) {
            return;
        }
        mediaRepository.deleteById(id);
    }
}
