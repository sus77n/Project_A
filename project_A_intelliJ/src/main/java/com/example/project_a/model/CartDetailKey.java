package com.example.project_a.model;


import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CartDetailKey implements Serializable {

    @Column()
    private int cartId;

    @Column()
    private int productId;

    // Getters, Setters, equals(), and hashCode()


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartDetailKey that = (CartDetailKey) o;
        return cartId == that.cartId && productId == that.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, productId);
    }
}
