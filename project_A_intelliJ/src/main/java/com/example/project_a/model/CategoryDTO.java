package com.example.project_a.model;

public class CategoryDTO {
    private Category category;
    private long productCount;

    public CategoryDTO(Category category, long productCount) {
        this.category = category;
        this.productCount = productCount;
    }

    public Category getCategory() {
        return category;
    }

    public long getProductCount() {
        return productCount;
    }
}
