package com.example.project_a.controller;

import com.example.project_a.model.Order;
import com.example.project_a.model.User;
import com.example.project_a.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class OrderController {
    @Autowired
    private OrderService service;

    @GetMapping("/admin/order/list")
    public String listAllOrders(Model model) {
        List<Order> orders = service.getAllOrders();
        model.addAttribute("orders", orders);
        model.addAttribute("pageTitle", "Order");
        return "admin/orders-list";
    }

}
