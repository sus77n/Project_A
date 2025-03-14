package com.example.project_a.service;


import com.example.project_a.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.project_a.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    public List<Product> getActiveProductsInActiveCategories() {
        return productRepository.findActiveProductsInActiveCategories();
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

    public void updateProduct(Product newProduct) {
        Product product = productRepository.findById(newProduct.getId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + newProduct.getId()));

        product.setName(newProduct.getName());
        product.setPrice(newProduct.getPrice());
        product.setDescription(newProduct.getDescription());
        product.setInStock(newProduct.getInStock());
        product.setStatus(newProduct.getStatus());
        product.setCategory(newProduct.getCategory());
        product.setSummary(newProduct.getSummary());
        product.setThumbnail(newProduct.getThumbnail());

        product.getProductSliders().clear();
        product.getProductSliders().addAll(newProduct.getProductSliders());

        productRepository.save(product);
    }

    public List<Product> getProductsByCategoryIds(List<Long> categoryIds) {
        return productRepository.findByCategoryIdIn(categoryIds);
    }

    public List<Product> searchProducts(String query) {
        return productRepository.searchActiveProductsInActiveCategories(query);
    }

    public void exportStock(int productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: "));
        product.setInStock(product.getInStock() - quantity);
        productRepository.save(product);
    }
    public void importStock(int productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: "));
        product.setInStock(product.getInStock() + quantity);
        productRepository.save(product);
    }
}
