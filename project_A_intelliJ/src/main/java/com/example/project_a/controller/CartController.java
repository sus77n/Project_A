package com.example.project_a.controller;

import com.example.project_a.model.Cart;
import com.example.project_a.model.Product;
import com.example.project_a.model.User;
import com.example.project_a.service.CartService;
import com.example.project_a.service.CategoryService;
import com.example.project_a.service.ProductService;
import com.example.project_a.service.UserService;
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
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;


    @GetMapping("/cart")
    public String showCart(Model model) {
        List<Cart> carts = service.getAllCarts();
        int total = 0;
        for (Cart cart : carts) {
            total += cart.getTotal();
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
    @GetMapping("/cart/add")
    public String addCart(@RequestParam("id") String id , RedirectAttributes ra) {
        int productId = Integer.parseInt(id);
        int userId = 1;
        if (service.isProductExist(productId, userId)){
            Cart cart = service.getCartByProductAndUser(productId, userId);
            cart.setQuantity(cart.getQuantity() + 1);
            service.save(cart);
        }else {
            Product product = productService.findProductById(Integer.parseInt(id));
            User user = userService.findUserById(1);
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setProduct(product);
            service.save(cart);
        }


        ra.addFlashAttribute("message", "The Cart has been added successfully.");
        return "redirect:/shop";
    }



}
