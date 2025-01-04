package com.example.project_a.repository;

import com.example.project_a.model.Cart;
import com.example.project_a.model.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Integer> {

}
