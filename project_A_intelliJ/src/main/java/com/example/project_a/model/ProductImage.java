package com.example.project_a.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name ="productImages")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "productImage_id", nullable = false, unique = true)
    private Integer productImage_id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "imageURL")
    private String imageURL;

    // Getters and Setters

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getProductImage_id() {
        return productImage_id;
    }

    public void setProductImage_id(Integer productImage_id) {
        this.productImage_id = productImage_id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    // ...



    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ProductImages [productImage_id=");
        builder.append(productImage_id);
        builder.append(", imageURL=");
        builder.append(imageURL);
        builder.append("Product Name: ");
        builder.append(product.getName());
        builder.append("]");
        return builder.toString();
    }
}


