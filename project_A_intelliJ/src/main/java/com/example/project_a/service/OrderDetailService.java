package com.example.project_a.service;

import com.example.project_a.model.Category;

import com.example.project_a.model.OrderDetail;
import com.example.project_a.repository.OrderDetailRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class OrderDetailService {
    @Autowired private OrderDetailRepository repo;

    public List<OrderDetail> getAllOrderDetails() {
        return (List<OrderDetail>) repo.findAll();
    }

    public void save(OrderDetail orderDetail) {
        repo.save(orderDetail);
    }

    public OrderDetail findOrderDetailById(Integer id) {
        Optional<OrderDetail> orderDetail = repo.findById(id);
        return orderDetail.orElse(null);
    }

    public void deleteOrderDetailById(Integer id) {
        if (findOrderDetailById(id) == null) {
            return;
        }
        repo.deleteById(id);
    }

    public void updateOrderDetailByID(String id, OrderDetail updatedOrderDetail) {
        // Fetch the existing orderDetail
        int orderDetailID = Integer.parseInt(id);
        OrderDetail orderDetail = repo.findById(orderDetailID)
                .orElseThrow(() -> new IllegalArgumentException("Order Detail not found with ID: " + id));

        // Update the fields of the existing orderDetail
        orderDetail.setOrder(updatedOrderDetail.getOrder());
        orderDetail.setProduct(updatedOrderDetail.getProduct());
        orderDetail.setQuantity(updatedOrderDetail.getQuantity());

        // Save the updated orderDetail
        repo.save(orderDetail);
    }

}
