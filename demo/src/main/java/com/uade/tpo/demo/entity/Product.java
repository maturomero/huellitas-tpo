package com.uade.tpo.demo.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Product {

    public Product(){}

    public Product(String name, double price, int stock){
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private boolean status = true;

    @ManyToMany
    @JoinTable(name = "product_animals", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "animal_id"))
    private List<Animal> animal;


    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
