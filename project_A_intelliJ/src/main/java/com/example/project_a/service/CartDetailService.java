package com.example.project_a.service;
import com.example.project_a.model.Cart;
import com.example.project_a.model.CartDetail;
import com.example.project_a.repository.CartDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class CartDetailService {
    @Autowired private CartDetailRepository repo;

    public List<CartDetail> getAllCartDetails() {
        return (List<CartDetail>) repo.findAll();
    }

    public void save(CartDetail cartDetail) {
        repo.save(cartDetail);
    }

    public CartDetail findCartDetail(Integer id) {
        Optional<CartDetail> cartDetail = repo.findById(id);
        return cartDetail.orElse(null);
    }

    public void deleteCartDetailById(Integer id) {
        if (findCartDetail(id) == null) {
            return;
        }
        repo.deleteById(id);
    }

    public void updateCartDetail(String id, CartDetail updatedCartDetail) {
        // Fetch the existing cartDetail
        int cartDetailID = Integer.parseInt(id);
        CartDetail cartDetail = repo.findById(cartDetailID)
                .orElseThrow(() -> new IllegalArgumentException("cartDetail not found with ID: " + id));
        // Update the fields of the existing cartDetail
        cartDetail.setQuantity(updatedCartDetail.getQuantity());
        // Save the updated cartDetail
        repo.save(cartDetail);
    }

}
