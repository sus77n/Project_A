package com.example.project_a.controller;

import com.example.project_a.model.*;
import com.example.project_a.service.OrderService;
import com.example.project_a.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class OrderController {
    @Autowired
    private OrderService service;
    @Autowired
    private ProductService ProductService;
    @Autowired
    private ProductService productService;

    @GetMapping("/admin/order/list")
    public String listAllOrders(Model model) {
        List<Order> orders = service.getAllOrders();
        model.addAttribute("orders", orders);
        model.addAttribute("pageTitle", "Order");
        return "admin/orders-list";
    }

    @GetMapping("/order/cancel")
    public String deleteCart(@RequestParam("id") String id , RedirectAttributes ra, @AuthenticationPrincipal User user) {
        Order order = service.findOrderById(Integer.parseInt(id));
        order.setPaymentStatus("Cancel");
        service.update(order);
        List<OrderDetail> orderDetails = order.getDetails();
        for(OrderDetail orderDetail : orderDetails) {
            Product product = ProductService.findProductById(orderDetail.getProduct().getId());
            productService.importStock(product.getId(), orderDetail.getQuantity());
        }
        ra.addFlashAttribute("message", "The Order has been canclled successfully.");
        return "redirect:/account#orders";
    }
}
