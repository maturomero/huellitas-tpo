package com.uade.tpo.demo.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String description;
}
