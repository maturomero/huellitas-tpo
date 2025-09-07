package com.uade.tpo.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.uade.tpo.demo.enums.PaymentMethod;
import com.uade.tpo.demo.enums.StatusOrder;

@Entity
@Data
@Table(name = "orders")

public class Order {

    public Order(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double totalPrice;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusOrder status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date date;

    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({
    "id",
    "password",
    "status",
    "role",
    "authorities",
    "accountNonExpired",
    "accountNonLocked",
    "credentialsNonExpired",
    "enabled",
    "username"})
    private User user;

    @OneToMany(mappedBy = "order")
    @JsonManagedReference
    private List<OrderProduct> orderProducts;
}
