package com.uade.tpo.demo.controllers;

import com.uade.tpo.demo.entity.OrderProduct;
import com.uade.tpo.demo.service.OrderProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("order-products")
public class OrderProductController {

    @Autowired
    private OrderProductServiceImpl orderProductService;

    // GET /order-products -> lista todos
    @GetMapping
    public ResponseEntity<List<OrderProduct>> getAll() {
        return ResponseEntity.ok(orderProductService.getOrderProducts());
    }

    // GET /order-products/{id} -> busca por id
    @GetMapping("/{id}")
    public ResponseEntity<OrderProduct> getById(@PathVariable Long id) {
        Optional<OrderProduct> result = orderProductService.getOrderProductById(id);
        return result.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE /order-products/{id} -> elimina por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderProductService.deleteOrderProduct(id);
        return ResponseEntity.noContent().build();
    }
}
