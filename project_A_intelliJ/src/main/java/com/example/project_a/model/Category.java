package com.example.project_a.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(nullable = false)
    private String categoryName;

    @Column(nullable = false)
    private String status;

    @Column()
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    public Category() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


//    @Override
//    public String toString() {
//        StringBuilder builder = new StringBuilder();
//        builder.append("Category [ID=");
//        builder.append(ID);
//        builder.append(", CategoryName=");
//        builder.append(CategoryName);
//        builder.append(", Product In The Category=");
//        for (Product product : products) {
//            builder.append(product.getName());
//            builder.append(", ");
//        }
//        builder.append("]");
//
//        return builder.toString();
//    }

}
