package com.uade.tpo.demo.entity.dto;

import java.util.List;



import lombok.Data;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private Double price;
    private Integer stock;
    private List<String> animal;
    private List<String> categrory;
}
