package com.example.project_a.model;

import jakarta.persistence.*;

@Entity
@Table(name = "media")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private int id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Product product;

    @Column(name = "image_url", nullable = false)
    private String ImageURL;

    @Column(name = "name")
    private String Name;

    @Column(name = "description")
    private String Description;

    @Column(name = "alter")
    private String Alter;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAlter() {
        return Alter;
    }

    public void setAlter(String alter) {
        Alter = alter;
    }
}
