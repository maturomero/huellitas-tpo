package com.uade.tpo.demo.entity.dto;

import java.util.List;

import com.uade.tpo.demo.entity.ProductImages;

import lombok.Data;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private double price;
    private int stock;
    private Long animalId;
    private Long categoryId;
    private List<ProductImages> images;
}
