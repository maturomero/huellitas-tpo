package com.uade.tpo.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uade.tpo.demo.entity.OrderProduct;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long>{
        @Query("SELECT p FROM OrderProduct p WHERE p.order.id = ?1")
        Optional<List<OrderProduct>> findByOrderId(Long id);
}
