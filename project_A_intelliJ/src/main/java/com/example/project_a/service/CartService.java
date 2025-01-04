package com.example.project_a.service;
import com.example.project_a.model.Cart;
import com.example.project_a.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class CartService {
    @Autowired private CartRepository repo;

    public List<Cart> getAllCarts() {
        return (List<Cart>) repo.findAll();
    }

    public void save(Cart cart) {
        repo.save(cart);
    }

    public Cart findCartById(Integer id) {
        Optional<Cart> cart = repo.findById(id);
        return cart.orElse(null);
    }

    public void deleteCartById(Integer id) {
        if (findCartById(id) == null) {
            return;
        }
        repo.deleteById(id);
    }

    public void updateCart(String id, Cart updatedCart) {
        // Fetch the existing cart
        int categoryId = Integer.parseInt(id);
        Cart cart = repo.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("cart not found with ID: " + id));
        // Update the fields of the existing cart
        cart.setUser(updatedCart.getUser());

        // Save the updated cart
        repo.save(cart);
    }

}
