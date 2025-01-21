package com.example.project_a.repository;

import com.example.project_a.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    @Query("SELECT c FROM Cart c WHERE c.product.id = :productId AND c.user.id = :userId")
    Cart findCartItemByProductAndUser(@Param("productId") Integer productId, @Param("userId") Integer userId);

    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId")
    List<Cart> findCartItemsByUser(@Param("userId") Integer userId);
}
