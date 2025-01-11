package com.example.project_a.model;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id", nullable = false, unique = true)
    private Integer ID;

    @Column(nullable = false)
    private String CategoryName;

    @Column(nullable = false)
    private String Status;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Column()
    private String Description;

    //RelationShip
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getCategoryname() {
        return CategoryName;
    }

    public void setCategoryname(String categoryName) {
        CategoryName = categoryName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
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
