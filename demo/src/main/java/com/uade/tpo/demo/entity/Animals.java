package com.uade.tpo.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;

@Entity
@Data
@Builder
public class Animals {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(nullable = false, unique = true)
    private String nombre;
}
 