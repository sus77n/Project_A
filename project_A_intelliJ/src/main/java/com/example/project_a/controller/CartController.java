package com.example.project_a.controller;

import com.example.project_a.model.Cart;
import com.example.project_a.service.CartService;
import com.example.project_a.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CartController {
    @Autowired
    private CartService service;


    @GetMapping("/cart")
    public String showCart(Model model) {
        List<Cart> carts = service.getAllCarts();
        int total = 0;
        for (Cart cart : carts) {
            total += cart.GetTotal();
        }
        model.addAttribute("carts", carts);
        model.addAttribute("total", total);
        return "shop/cart";
    }
    @GetMapping("/cart/delete")
    public String deleteCart(@RequestParam("id") String id , RedirectAttributes ra) {
        service.deleteCartById(Integer.parseInt(id));
        ra.addFlashAttribute("message", "The Cart has been deleted successfully.");
        return "redirect:/cart";
    }

}
