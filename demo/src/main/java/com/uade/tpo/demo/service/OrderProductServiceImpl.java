package com.uade.tpo.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.OrderProduct;
import com.uade.tpo.demo.exceptions.OrderNotExistException;
import com.uade.tpo.demo.exceptions.OrderProductNotExistException;
import com.uade.tpo.demo.repository.OrderProductRepository;
@Service
public class OrderProductServiceImpl implements OrderProductService{
    @Autowired
    private OrderProductRepository orderProductRepository;

    public OrderProduct createOrderProduct(OrderProduct orderProduct){
        return orderProductRepository.save(orderProduct);
    }

    public Optional<OrderProduct> getOrderProductById(Long id) throws OrderProductNotExistException {
        Optional<OrderProduct> oPR = orderProductRepository.findById(id);
        if(oPR.isEmpty()){
            throw new OrderProductNotExistException();
        }
        return oPR;
    }

    public List<OrderProduct> getAllOrderProducts() {
        return orderProductRepository.findAll();
    }

    public List<OrderProduct> getOrderProductByOrderId(Long orderId) throws OrderNotExistException {
        List<OrderProduct> oP = orderProductRepository.findByOrderId(orderId).get();
        if(oP.isEmpty()){
            throw new OrderNotExistException();
        }
        return oP;
    }
    
}
