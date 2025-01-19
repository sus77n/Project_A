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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "thumbnail", referencedColumnName = "id")
    private Media thumbnail;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> carts;

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


