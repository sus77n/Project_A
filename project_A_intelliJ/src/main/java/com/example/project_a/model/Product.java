package com.example.project_a.model;

import com.fasterxml.jackson.databind.util.NativeImageUtil;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id", nullable = false, unique = true)
    private Integer product_id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Product> productImages = new ArrayList<>();


    // Other fields and their annotations
    @Column(name = "description")
    private String description;

    @Column(name = "summary")
    private String summary;

    @Column(name = "in_stock")
    private Integer inStock;

    @Column(name = "price")
    private Integer price;

    // Getters and Setters
    // ...


    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getInStock() {
        return inStock;
    }

    public void setInStock(Integer inStock) {
        this.inStock = inStock;
    }

    public Integer getPrice() {

        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Product [product_id=");
        builder.append(product_id);
        builder.append(", name=");
        builder.append(name);
        builder.append(", category=");
        builder.append(category.getCategoryname());
        builder.append(", description=");
        builder.append(description);
        builder.append(", summary=");
        builder.append(summary);
        builder.append(", inStock=");
        builder.append(inStock);
        builder.append(", price=");
        builder.append(price);
        builder.append("]");

        return builder.toString();
    }
}


