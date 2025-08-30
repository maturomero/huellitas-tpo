package com.uade.tpo.demo.entity.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private Double price;
    private Integer stock;
    private List<Long> animalId;
    private Long categoryId;
}
