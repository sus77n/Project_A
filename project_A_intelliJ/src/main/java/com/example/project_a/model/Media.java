package com.example.project_a.model;

import jakarta.persistence.*;

@Entity
@Table(name = "media")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private int id;

    @OneToOne
    @JoinColumn(name ="product_id")
    private Product product;

    @Column(name = "image_url", nullable = false)
    private String imageURL;

    @Column(name = "name")
    private String name;

    @Column(name = "alt")
    private String alt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alter) {
        this.alt = alter;
    }
}
