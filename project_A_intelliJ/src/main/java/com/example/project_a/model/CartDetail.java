package com.example.project_a.model;

import jakarta.persistence.*;

@Entity
@Table(name ="cartDetails")
public class CartDetail {
    @EmbeddedId
    private CartDetailKey id;
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "cart_id", nullable = false, unique = true)
//    private Integer ID;

    @Column( nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn()
    private Cart cart;

    @ManyToOne
    @JoinColumn()
    private Product product;

    //Getter Setter


    public CartDetailKey getId() {
        return id;
    }

    public void setId(CartDetailKey id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

