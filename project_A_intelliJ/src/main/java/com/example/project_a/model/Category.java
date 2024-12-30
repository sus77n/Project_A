package com.example.project_a.model;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name ="categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id", nullable = false, unique = true)
    private Integer categoryId;

    @Column( nullable = false)
    private String categoryName;

    @Column()
    private String status;


    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Product> products;


    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Integer getID() {
        return categoryId;
    }

    public void setID(Integer ID) {
        this.categoryId = ID;
    }

    public String getCategoryname() {
        return categoryName;
    }

    public void setCategoryname(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Category [categoryId=");
        builder.append(categoryId);
        builder.append(", categoryName=");
        builder.append(categoryName);
        builder.append(", status=");
        builder.append(status);
        builder.append("]");

        for (Product product : products) {
            builder.append("\n");
            builder.append(product);
        }

        return builder.toString();
    }
}
