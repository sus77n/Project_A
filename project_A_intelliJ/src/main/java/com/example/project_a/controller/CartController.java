package com.example.project_a.controller;

import com.example.project_a.model.Cart;
import com.example.project_a.model.Product;
import com.example.project_a.model.User;
import com.example.project_a.service.CartService;
import com.example.project_a.service.CategoryService;
import com.example.project_a.service.ProductService;
import com.example.project_a.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class CartController {
    @Autowired
    private CartService service;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;


    @GetMapping("/cart")
    public String showCart(Model model, HttpSession session) {
        List<Cart> cartList = (List<Cart>) session.getAttribute("cartList");
        for (Cart cart : cartList) {
            System.out.println(cart.getProduct().getName());
        }
        if (cartList == null) {
            cartList = new ArrayList<>();
        }

        int total = cartList.stream().mapToInt(Cart::getTotal).sum();

        model.addAttribute("carts", cartList);
        model.addAttribute("total", total);

        return "shop/cart"; // Make sure this points to your Thymeleaf cart page
    }

    @GetMapping("/cart/delete")
    public String deleteCart(@RequestParam("id") String id , RedirectAttributes ra) {
        service.deleteCartById(Integer.parseInt(id));
        ra.addFlashAttribute("message", "The Cart has been deleted successfully.");
        return "redirect:/cart";
    }

    @PostMapping("/cart/add")
    @ResponseBody
    public Map<String, Object> addToCart(@RequestBody Map<String, Integer> request, HttpSession session) {
        Integer productId = request.get("id");

        if (productId == null) {
            throw new IllegalArgumentException("Product ID is required");
        }

        // Retrieve the cart list from the session (or create a new one)
        List<Cart> cartList = (List<Cart>) session.getAttribute("cartList");
        if (cartList == null) {
            cartList = new ArrayList<>();
        }

        // Check if product is already in cart
        Cart existingCartItem = null;
        for (Cart item : cartList) {
            if (item.getProduct().getId().equals(productId)) {
                existingCartItem = item;
                break;
            }
        }

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
        } else {
            Cart newCartItem = new Cart();
            newCartItem.setProduct(productService.findProductById(productId));
            newCartItem.setQuantity(1);
            cartList.add(newCartItem);
        }

        session.setAttribute("cartList", cartList);

        // Calculate total cart size
        int totalCartItems = cartList.stream().mapToInt(Cart::getQuantity).sum();

        // Build response
        Map<String, Object> response = new HashMap<>();
        response.put("cartSize", totalCartItems);
        response.put("cartItems", cartList.stream()
                .map(cartItem -> {
                    Map<String, Object> itemMap = new HashMap<>();
                    itemMap.put("productName", cartItem.getProduct().getName());  // Add the name property
                    itemMap.put("quantity", cartItem.getQuantity());
                    itemMap.put("price", cartItem.getQuantity() * cartItem.getQuantity());
                    return itemMap;
                })
                .collect(Collectors.toList()));  // Convert to a list of maps containing properties

        for (Cart cartItem : cartList) {
            System.out.println(cartItem.getProduct().getId());
        }
        return response;
    }






}
