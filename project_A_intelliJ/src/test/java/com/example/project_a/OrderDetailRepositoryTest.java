package com.example.project_a;

import com.example.project_a.model.Order;
import com.example.project_a.model.User;
import com.example.project_a.repository.OrderRepository;
import com.example.project_a.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)


public class OrderDetailRepositoryTest {

    @Autowired private OrderRepository repo;
    @Autowired private UserRepository userRepo;
    @Test
    public void AddNewOrder() {
        Order order = new Order();
        User user = getUserByID(402);
        order.setUser(user);
        order.setFormOfPayment("Card");
        order.setOrderDate();
        order.setPaymentStatus("Not Yet");

        Order savedOrder = repo.save(order);
        Assertions.assertNotNull(savedOrder);
        Assertions.assertTrue(savedOrder.getID() > 0, "User ID should be greater than 0");

    }
    //Helper function
    public User getUserByID(Integer id) {
        Optional<User> optional = userRepo.findById(id);
        Assertions.assertTrue(optional.isPresent(), "User should be present");
        User user = optional.get();
        return user;
    }
    //End Helper Function

    @Test
    public void testListAllOrders() {
        Iterable<Order> orders = repo.findAll();
        Assertions.assertTrue(orders.iterator().hasNext(), "Users collection should contain at least one user");

        for (Order order : orders) {
            System.out.println(order);
        }
    }


}

