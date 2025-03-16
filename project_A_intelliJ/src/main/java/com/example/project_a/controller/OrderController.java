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

        // Sort orders based on custom logic
        orders.sort((o1, o2) -> {
            int priority1 = getStatusPriority(o1.getPaymentStatus());
            int priority2 = getStatusPriority(o2.getPaymentStatus());

            // Sort by priority first
            if (priority1 != priority2) {
                return Integer.compare(priority1, priority2);
            }

            // If status is "New", sort by orderDate ascending (oldest first)
            if ("New".equals(o1.getPaymentStatus())) {
                return o1.getOrderDate().compareTo(o2.getOrderDate());
            }

            // If status is "Done" or "Cancel", sort by orderDate descending (newest first)
            return o2.getOrderDate().compareTo(o1.getOrderDate());
        });

        model.addAttribute("orders", orders);
        model.addAttribute("pageTitle", "Order");
        return "admin/orders-list";
    }

    // Helper method to assign priority to order status
    private int getStatusPriority(String status) {
        switch (status) {
            case "New": return 1;    // Highest priority
            case "Done": return 2;
            case "Cancel": return 3; // Lowest priority
            default: return 4;       // Any unknown status comes last
        }
    }

    @GetMapping("/admin/order/done")
    public String doneOrder(@RequestParam("id") String id , RedirectAttributes ra, @AuthenticationPrincipal User user) {
        Order order = service.findOrderById(Integer.parseInt(id));
        order.setPaymentStatus("Done");
        service.update(order);
        List<OrderDetail> orderDetails = order.getDetails();
        ra.addFlashAttribute("message", "The Order has been done successfully.");
        return "redirect:/admin/order/list";
    }

    @GetMapping("/order/cancel")
    public String cancelOrder(@RequestParam("id") String id , RedirectAttributes ra, @AuthenticationPrincipal User user) {
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
