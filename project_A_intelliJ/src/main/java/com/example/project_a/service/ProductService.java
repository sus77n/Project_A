package com.example.project_a.service;


import com.example.project_a.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.project_a.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }


    public void save(Product product) {
        productRepository.save(product);
    }

    public Product findProductById(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    public void deleteProductById(Integer id) {
        if (findProductById(id) == null) {
            return;
        }
        productRepository.deleteById(id);
    }

}
