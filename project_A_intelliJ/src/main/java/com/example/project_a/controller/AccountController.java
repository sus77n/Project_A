package com.example.project_a.controller;

import com.example.project_a.model.Order;
import com.example.project_a.model.OrderDetail;
import com.example.project_a.service.OrderService;
import com.example.project_a.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AccountController {
    @Autowired
    private OrderService orderService;
    @GetMapping("/account")
    public String showAccount(Model model) {
        List<Order> orders = orderService.findOrdersByUserId(1);
        model.addAttribute("orders", orders);
        for (Order order : orders) {
            List<OrderDetail> orderDetails = order.getDetails();
            System.out.println("toitimkiem");
            for (OrderDetail orderDetail : orderDetails) {
                System.out.println("neukhongnull");
                System.out.println(orderDetail.getID());
            }
        }

        return "shop/account";
    }
}
