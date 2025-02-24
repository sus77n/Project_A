package com.example.project_a.service;

import com.example.project_a.model.Order;
import com.example.project_a.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<Order> findOrdersByUserId(Integer id) {
        List<Order> orderList = repo.findAll();
        List<Order> orders = new ArrayList<>();
        if (!orderList.isEmpty()) {
           for (Order existOrder : orderList) {
                if(existOrder.getUser().getId().equals(id)){
                    orders.add(existOrder);
                }
           }
        }
        return orders;
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
