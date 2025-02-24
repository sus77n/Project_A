package com.example.project_a.repository;

import com.example.project_a.model.Product;
import com.example.project_a.model.ProductSlider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSliderRepository extends JpaRepository<ProductSlider, Integer> {
    List<ProductSlider> findByProduct(Product product);
}