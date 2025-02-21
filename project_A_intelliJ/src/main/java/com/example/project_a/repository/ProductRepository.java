package com.example.project_a.repository;

import com.example.project_a.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategoryIdIn(List<Long> categoryIds);

    @Query("SELECT COUNT(p) FROM Product p WHERE p.category.id = :categoryId")
    long countProductsByCategory(@Param("categoryId") Integer categoryId);

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.productSliders WHERE p.id = :id")
    Optional<Product> findByIdWithSliders(@Param("id") Integer id);

}
