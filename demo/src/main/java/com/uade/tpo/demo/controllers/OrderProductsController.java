package com.uade.tpo.demo.controllers;

import com.uade.tpo.demo.entity.OrderProduct;
import com.uade.tpo.demo.exceptions.NoEntitiesFoundException;
import com.uade.tpo.demo.exceptions.OrderNotExistException;
import com.uade.tpo.demo.exceptions.OrderProductNotExistException;
import com.uade.tpo.demo.service.OrderProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/order_products")
public class OrderProductsController {

    @Autowired
    private OrderProductService orderProductService;

    @GetMapping
    public List<OrderProduct> getAllOrderProducts() throws NoEntitiesFoundException {
        return orderProductService.getAllOrderProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderProduct> getOrderProductById(@PathVariable Long id) throws OrderProductNotExistException {
        Optional<OrderProduct> orderProduct = orderProductService.getOrderProductById(id);
        if (orderProduct.isPresent()) {
            return ResponseEntity.ok(orderProduct.get());
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/orders/{orderId}")
    public List<OrderProduct> getOrderProductByOrderId(@PathVariable Long orderId) throws OrderNotExistException {
        return orderProductService.getOrderProductByOrderId(orderId);
    }

    @PostMapping
    public ResponseEntity<OrderProduct> createOrderProduct(@RequestBody OrderProduct orderProduct) {
        OrderProduct created = orderProductService.createOrderProduct(orderProduct);
        return ResponseEntity.ok(created);
    }
}
