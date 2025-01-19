package com.example.project_a.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name ="products")
@ToString(exclude = {"thumbnail", "orderDetails"})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false, unique = true)
    private Integer id;

    @Column( nullable = false, length = 50)
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails;

    // Other fields and their annotations
    @Column(name = "description")
    private String description;

    @Column(name = "summary")
    private String summary;

    @Column(name = "in_stock")
    private Integer inStock;

    @Column(name = "price")
    private Double price;

    @Column(name = "status")
    private String status;

    @Column(name = "product_slider")
    private String productSlider;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Media thumbnail;



    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> carts;

    // Getters and Setters


    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    public Integer getProduct_id() {
        return id;
    }

    public void setProduct_id(Integer product_id) {
        this.id = product_id;
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

    public Double getPrice() {

        return price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }


    public void setPrice(Double price) {
        this.price = price;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Product [product_id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append(", category=");
        builder.append(category.getCategoryName());
        builder.append(", description=");
        builder.append(description);
        builder.append(", summary=");
        builder.append(summary);
        builder.append(", inStock=");
        builder.append(inStock);
        builder.append(", price=");
        builder.append(price);
        builder.append(", URL=");
        builder.append("\n");
        builder.append("]");

        return builder.toString();
    }
}


