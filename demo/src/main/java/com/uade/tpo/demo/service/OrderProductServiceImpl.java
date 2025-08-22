package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.OrderProduct;
import com.uade.tpo.demo.repository.OrderProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderProductServiceImpl {

    @Autowired
    private OrderProductRepository repo;

    // Listamos todos los order products
    public List<OrderProduct> getOrderProducts() {
        return repo.findAll();
    }

    // Buscamos por algún ID específico
    public Optional<OrderProduct> getOrderProductById(Long id) {
        return repo.findById(id);
    }

    // Eliminar por id
    public void deleteOrderProduct(Long id) {
        repo.deleteById(id);
    }
}
