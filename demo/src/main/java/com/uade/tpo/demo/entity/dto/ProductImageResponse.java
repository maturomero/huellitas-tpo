package com.uade.tpo.demo.entity.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductImageResponse {
    private Long id;
    private String file;
}