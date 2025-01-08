package com.example.project_a.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name ="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id" ,nullable = false, unique = true)
    private Integer ID;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> details;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "form_of_payment", length = 15)
    private String formOfPayment;

    @Column(name = "payment_status", length = 7)
    private String paymentStatus;

    @Column(name = "order_date")
    @Temporal(TemporalType.DATE)
    private Date orderDate;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFormOfPayment() {
        return formOfPayment;
    }

    public void setFormOfPayment(String formOfPayment) {
        this.formOfPayment = formOfPayment;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    public void setOrderDate() {
        Date currentDate = new Date(5);
        this.orderDate = currentDate;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Order [ID=");
        builder.append(ID);
        builder.append(", formOfPayment=");
        builder.append(formOfPayment);
        builder.append(", client id=");
        builder.append(user.getID());
        builder.append(", paymentStatus=");
        builder.append(paymentStatus);
        builder.append(", orderDate=");
        builder.append(orderDate);
        builder.append("]");

        return builder.toString();
    }
}
