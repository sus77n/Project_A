package com.example.project_a.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id" ,nullable = false, unique = true)
    private Integer id;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> details = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id")
    private User user;

    @Column(name = "form_of_payment", length = 15)
    private String formOfPayment;

    @Column(name = "payment_status", length = 7)
    private String paymentStatus;

    @Column(name = "order_date")
    @Temporal(TemporalType.DATE)
    private Date orderDate;

    @Getter
    @Setter
    @Column(name = "phone_number")
    private String phoneNumber;

    @Getter
    @Setter
    @Column(name = "address")
    private String address;

    public Integer getID() {
        return id;
    }

    public Integer getNumeberOfItems() {
        int count = 0;
        for (OrderDetail detail : details) {
            count += detail.getQuantity();
        }
        return count;
    }
    public  double getTotal(){
        double total = 0.0;
        for (OrderDetail detail : details) {
            total += detail.getQuantity() * detail.getProduct().getPrice();
        }
        return total;
    }
    public void setID(Integer ID) {
        this.id = ID;
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
        Date currentDate = new Date(); // This sets the current date and time
        this.orderDate = currentDate;
    }
    public List<OrderDetail> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetail> details) {
        this.details = details;
    }
    public void addDetail(OrderDetail detail) {
        details.add(detail);
        detail.setOrder(this);
    }


}
