package com.example.project_a.repository;

import com.example.project_a.model.CartDetail;
import com.example.project_a.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
}
