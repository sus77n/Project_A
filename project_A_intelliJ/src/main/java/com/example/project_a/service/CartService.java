package com.example.project_a.service;
import com.example.project_a.model.CartItem;
import com.example.project_a.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class CartService {
    @Autowired private CartRepository repo;

    public List<CartItem> getAllCarts() {
        return (List<CartItem>) repo.findAll();
    }

    public void save(CartItem cart) {
        repo.save(cart);
    }

    public CartItem findCartById(Integer id) {
        Optional<CartItem> cart = repo.findById(id);
        return cart.orElse(null);
    }

    public void deleteCartById(Integer id) {
        if (findCartById(id) == null) {
            return;
        }
        repo.deleteById(id);
    }

    public void updateCart(String id, CartItem updatedCart) {
        // Fetch the existing cart
        int categoryId = Integer.parseInt(id);
        CartItem cart = repo.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("cart not found with ID: " + id));
        // Update the fields of the existing cart
        cart.setUser(updatedCart.getUser());

        // Save the updated cart
        repo.save(cart);
    }

}
