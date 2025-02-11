package com.example.project_a.controller;

import com.example.project_a.model.*;
import com.example.project_a.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CheckOutController {

    @Autowired
    private OrderService service;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderDetailService orderDetailService;


    @GetMapping("/checkout")
    public String showCheckout(Model model) {
        User user = userService.findUserById(1);
        Order order = new Order();
        order.setUser(user);
        order.setAddress(user.getAddress());
        order.setPhoneNumber(user.getPhoneNumber());
        order.setPaymentStatus("Unpaid");
        List<Cart> carts = cartService.getCartsByUserId(user.getId());
        int total = 0;
        for (Cart cart : carts) {
            total += cart.getTotal();
        }
        model.addAttribute("total", total);
        model.addAttribute("order", order);
        model.addAttribute("carts", carts);
        model.addAttribute("updatedOrder", new Order());
        return "shop/checkout";
    }

    @PostMapping("/order/save")
    public String saveOrder(Order order, RedirectAttributes ra) {
        service.save(order);
        int userId = order.getUser().getId();
        List<Cart> carts = cartService.getCartsByUserId(userId);
        for (Cart cart : carts) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(cart.getProduct());
            orderDetail.setQuantity(cart.getQuantity());
            order.addDetail(orderDetail);
            orderDetailService.save(orderDetail);
        }
        cartService.clear(userId);
        ra.addFlashAttribute("message", "The Order has been saved successfully.");
        return "redirect:/shop";
    }
}
