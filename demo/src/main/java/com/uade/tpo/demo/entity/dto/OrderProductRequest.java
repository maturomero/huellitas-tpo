package com.uade.tpo.demo.entity.dto;
import lombok.Data;

@Data

public class OrderProductRequest {
    private Long productId;
    private int units;
}
