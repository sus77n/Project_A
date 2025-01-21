package com.example.project_a.service;

import com.example.project_a.model.Order;
import com.example.project_a.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired private OrderRepository repo;

    public List<Order> getAllOrders() {
        return (List<Order>) repo.findAll();
    }


    public void save(Order order) {
        repo.save(order);
    }

    public Order findOrderById(Integer id) {
        Optional<Order> order = repo.findById(id);
        return order.orElse(null);
    }

    public void deleteOrderById(Integer id) {
        if (findOrderById(id) == null) {
            return;
        }
        repo.deleteById(id);
    }
    public boolean isExits(Integer id) {
        if (findOrderById(id) == null) {
            return true;
        }else {
            return false;
        }
    }

    public void update(Order Updatedorder) {
        Order order = findOrderById(Updatedorder.getID());
        order = Updatedorder;
        repo.save(order);
    }
}
