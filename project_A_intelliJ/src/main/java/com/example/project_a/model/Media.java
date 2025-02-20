package com.example.project_a.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "`media`")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @OneToOne(mappedBy = "thumbnail", optional = true)
    private Product product;

    @Column(name = "image_url", nullable = false)
    private String imageURL;

    @Column(name = "type")
    private String type;

    @Column(name = "alt")
    private String alt;

    @Override
    public String toString() {
        return "Media{" +
                "id=" + id +
                ", product=" + product +
                ", imageURL='" + imageURL + '\'' +
                ", name='" + type + '\'' +
                ", alt='" + alt + '\'' +
                '}';
    }
}
