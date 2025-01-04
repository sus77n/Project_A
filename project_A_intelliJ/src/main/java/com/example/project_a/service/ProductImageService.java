package com.example.project_a.service;


import com.example.project_a.model.Product;
import com.example.project_a.model.ProductImage;
import com.example.project_a.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.project_a.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductImageService {
    @Autowired private ProductImageRepository repo;

    public List<ProductImage> getAllProductImages() {
        return (List<ProductImage>) repo.findAll();
    }


    public void save(ProductImage productImage) {
        repo.save(productImage);
    }

    public ProductImage findProductImageById(Integer id) {
        Optional<ProductImage> productImage = repo.findById(id);
        return productImage.orElse(null);
    }

    public void deleteProductImageById(Integer id) {
        if (findProductImageById(id) == null) {
            return;
        }

        repo.deleteById(id);
    }

}
