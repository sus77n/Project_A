package com.example.project_a.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`media`")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "type")
    private String type;

    @Column(name = "alt")
    private String alt;

    @Override
    public String toString() {
        return "Media{" +
                "id=" + id +
                ", product=" + product +
                ", imageURL='" + filePath + '\'' +
                ", name='" + type + '\'' +
                ", alt='" + alt + '\'' +
                '}';
    }
}
