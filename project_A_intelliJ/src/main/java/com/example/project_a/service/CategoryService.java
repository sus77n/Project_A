package com.example.project_a.service;
import com.example.project_a.model.Category;
import com.example.project_a.model.CategoryDTO;
import com.example.project_a.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.project_a.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class CategoryService {
    @Autowired private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<Category> getAllCategories() {
        return (List<Category>) categoryRepository.findAll();
    }

    public List<Category> getActiveCategories() {
        return categoryRepository.findByStatus("Active");
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public Category findCategoryById(Integer id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.orElse(null);
    }

    public void deleteCategoryById(Integer id) {
        if (findCategoryById(id) == null) {
            return;
        }
        categoryRepository.deleteById(id);
    }

    public void updateCategory(String id, Category updatedCategory) {
        // Fetch the existing category
        int categoryId = Integer.parseInt(id);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: " + id));

        // Update the fields of the existing category
        category.setCategoryName(updatedCategory.getCategoryName());
        category.setDescription(updatedCategory.getDescription());
        category.setStatus(updatedCategory.getStatus());

        // Save the updated category
        categoryRepository.save(category);
    }

    public List<CategoryDTO> getAllCategoriesWithProductCount() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map(category -> {
            long productCount = productRepository.countProductsByCategory(category.getId());
            return new CategoryDTO(category, productCount);
        }).collect(Collectors.toList());
    }

}
