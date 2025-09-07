package com.uade.tpo.demo.entity.dto;

import java.util.List;

import com.uade.tpo.demo.enums.PaymentMethod;

import lombok.Data;

@Data
public class OrderRequest{
    private Long userId;
    private PaymentMethod paymentMethod;
    private List<OrderProductRequest> orderProductRequest;
}
