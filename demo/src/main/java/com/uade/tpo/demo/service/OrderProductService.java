package com.uade.tpo.demo.service;

import java.util.List;
import java.util.Optional;

import com.uade.tpo.demo.entity.OrderProduct;

public interface OrderProductService {
    public OrderProduct createOrderProduct(OrderProduct orderProduct);
    public Optional<OrderProduct> getOrderProductById(Long id);
    public List<OrderProduct> getAllOrderProducts();
    public List<OrderProduct> getOrderProductByOrderId(Long orderId);
}
