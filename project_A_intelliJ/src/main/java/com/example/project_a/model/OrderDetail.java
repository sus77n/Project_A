package com.example.project_a.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name ="ordersDetails")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_detail_id" ,nullable = false, unique = true)
    private Integer ID;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity", nullable = false)
    private  Integer quantity;

    //Seter Geter

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("OrderDetail [ID=");
        builder.append(ID);
        builder.append(", orderID=");
        builder.append(order.getID());
        builder.append(", productID=");
        builder.append(product.getProduct_id());
        return builder.toString();
    }
}
