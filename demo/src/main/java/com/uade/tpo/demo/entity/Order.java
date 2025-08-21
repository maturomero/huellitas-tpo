package com.uade.tpo.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

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
    @Column(nullable = false)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
