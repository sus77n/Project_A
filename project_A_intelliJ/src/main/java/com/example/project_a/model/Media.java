package com.example.project_a.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "media")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private int id;

    @OneToOne(mappedBy = "thumbnail", optional = false)
    private Product product;

    @Column(name = "image_url", nullable = false)
    private String imageURL;

    @Column(name = "name")
    private String name;

    @Column(name = "alt")
    private String alt;

    @Override
    public String toString() {
        return "Media{" +
                "id=" + id +
                ", product=" + product +
                ", imageURL='" + imageURL + '\'' +
                ", name='" + name + '\'' +
                ", alt='" + alt + '\'' +
                '}';
    }
}
