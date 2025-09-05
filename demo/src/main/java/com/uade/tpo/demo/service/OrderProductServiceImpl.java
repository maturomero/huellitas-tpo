package com.uade.tpo.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.OrderProduct;
import com.uade.tpo.demo.repository.OrderProductRepository;
@Service
public class OrderProductServiceImpl implements OrderProductService{
    @Autowired
    private OrderProductRepository orderProductRepository;

    public OrderProduct createOrderProduct(OrderProduct orderProduct){
        return orderProductRepository.save(orderProduct);
    }

    public Optional<OrderProduct> getOrderProductById(Long id) {
        return orderProductRepository.findById(id);
    }

    public List<OrderProduct> getAllOrderProducts() {
        return orderProductRepository.findAll();
    }

     public List<OrderProduct> getOrderProductByOrderId(Long orderId) {
        return orderProductRepository.findByOrderId(orderId).get();
    }
    
}
