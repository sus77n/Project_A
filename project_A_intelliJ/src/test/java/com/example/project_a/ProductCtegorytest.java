package com.example.project_a;

import com.example.project_a.model.Category;
import com.example.project_a.repository.CategoryRepository;
import com.example.project_a.repository.UserRepository;

import com.example.project_a.model.Product;
import com.example.project_a.model.User;
import com.example.project_a.repository.ProductRepository;
import com.example.project_a.repository.UserRepository;
import com.example.project_a.service.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;


import com.example.project_a.model.Category;
import com.example.project_a.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.Optional;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)


public class ProductCtegorytest {

    @Autowired private ProductRepository repo;
    @Autowired private CategoryRepository Caterepo;
    @Test
    public void AddNewProduct() {
        Product product = new Product();
        product.setName("ShoeCu");
        product.setSummary("ShoeFe");
        product.setDescription("ShoeFe");
        product.setPrice(6.0);
        product.setInStock(6);
        Iterable<Category> categories = Caterepo.findAll();
        Category category = new Category();
        for (Category categoryForEach : categories) {
            category = categoryForEach;
        }
        product.setCategory(category);

        Product savedProduct = repo.save(product);
        Assertions.assertNotNull(savedProduct);
        Assertions.assertTrue(savedProduct.getProduct_id() > 0, "User ID should be greater than 0");

    }


}
