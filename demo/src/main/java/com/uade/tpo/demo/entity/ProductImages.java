package com.uade.tpo.demo.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Data;

//No usamos builder
@Entity
@Data
public class ProductImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String urlImage;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference //esta notacion es para marcar quien es el hijo en la relacion bidireccional (sino generaria un loop)
    private Product product;
}