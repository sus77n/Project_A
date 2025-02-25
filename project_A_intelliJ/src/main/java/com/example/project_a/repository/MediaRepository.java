package com.example.project_a.repository;

import com.example.project_a.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<Media, Integer> {
    void deleteByFilePath(String fileName);
}
