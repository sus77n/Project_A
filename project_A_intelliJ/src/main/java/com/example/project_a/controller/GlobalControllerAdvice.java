package com.example.project_a.controller;

import com.example.project_a.model.Cart;
import com.example.project_a.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalControllerAdvice {
    @Autowired
    private CartService service;

    @ModelAttribute("cartData")
    public Map<String, Object> getCartData() {
        String userId = "1"; // Fetch user ID from authentication/session

        if (userId == null) {
            return Collections.emptyMap(); // Return empty data if no user is authenticated
        }

        List<Cart> carts = service.getCartsByUserId(Integer.parseInt(userId));
        double total = service.calculateTotal(Integer.parseInt(userId)); // Update with taxes/shipping if applicable
        int numberOfItems = carts.size();
        Map<String, Object> cartData = new HashMap<>();
        cartData.put("cartItems", carts);
        cartData.put("total", total);
        cartData.put("numberOfItems", numberOfItems);
        return cartData;
    }
}
