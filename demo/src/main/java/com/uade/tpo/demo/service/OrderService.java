package com.uade.tpo.demo.service;

import java.util.List;
import java.util.Optional;

import com.uade.tpo.demo.entity.Order;
import com.uade.tpo.demo.entity.dto.OrderRequest;
import com.uade.tpo.demo.exceptions.InsufficientStockException;
import com.uade.tpo.demo.exceptions.NoEntitiesFoundException;
import com.uade.tpo.demo.exceptions.OrderNotExistException;
import com.uade.tpo.demo.exceptions.ProductNotExistException;
import com.uade.tpo.demo.exceptions.UserNotFoundException;

public interface OrderService {
    public Optional<Order> getOrderById(Long id) throws OrderNotExistException;
    public List<Order> getAllOrders() throws NoEntitiesFoundException;
    public Order createOrder(OrderRequest orderRequest) throws InsufficientStockException, ProductNotExistException, UserNotFoundException;
    public Order deleteOrderById(Long id) throws OrderNotExistException;
}
