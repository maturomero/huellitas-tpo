package com.uade.tpo.demo.service;

import java.util.List;
import java.util.Optional;

import com.uade.tpo.demo.entity.OrderProduct;
import com.uade.tpo.demo.exceptions.NoEntitiesFoundException;
import com.uade.tpo.demo.exceptions.OrderNotExistException;
import com.uade.tpo.demo.exceptions.OrderProductNotExistException;

public interface OrderProductService {
    public OrderProduct createOrderProduct(OrderProduct orderProduct);
    public Optional<OrderProduct> getOrderProductById(Long id) throws OrderProductNotExistException;
    public List<OrderProduct> getAllOrderProducts() throws NoEntitiesFoundException;
    public List<OrderProduct> getOrderProductByOrderId(Long orderId) throws OrderNotExistException;
}
