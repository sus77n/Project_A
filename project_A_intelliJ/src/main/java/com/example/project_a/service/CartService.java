package com.example.project_a.service;
import com.example.project_a.model.Cart;
import com.example.project_a.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
        int cartId = Integer.parseInt(id);
        Cart cart = repo.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("cart not found with ID: " + id));
        // Update the fields of the existing cart
        System.out.println("checkCartService");
        System.out.println(cart.getQuantity());
        System.out.println(updatedCart.getQuantity());
        cart.setQuantity(updatedCart.getQuantity());
        // Save the updated cart
        repo.save(cart);
    }

    public Cart getCartByProductAndUser(Integer productId, Integer userId) {
        return repo.findCartItemByProductAndUser(productId, userId);
    }
    public List<Cart> getCartsByUserId(Integer userId) {
        return repo.findCartItemsByUser(userId);
    }
    public BigDecimal calculateTotal(Integer userId) {
        List<Cart> carts = getCartsByUserId(userId);
        BigDecimal total = BigDecimal.ZERO;
        for (Cart cart : carts) {
            total = total.add(cart.getTotal());
        }
        return total;

    }

    public boolean isProductExist(Integer productId, Integer userId) {
       if (getCartByProductAndUser(productId, userId) != null) {
           return true;
       }else {
           return false;
       }
    }

    public void clear(int userId) {
        List<Cart> carts =getCartsByUserId(userId);
        for (Cart cart : carts) {
            repo.deleteById(cart.getId());
        }
    }
}
