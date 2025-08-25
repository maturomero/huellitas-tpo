package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Order;
import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.enums.StatusOrder;
import com.uade.tpo.demo.exceptions.OrderNotExistException;
import com.uade.tpo.demo.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl {

    @Autowired
    private OrderRepository orderRepository;

    // ========================
    // Operaciones básicas CRUD
    // ========================

    // Devuelve todas las órdenes
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    // Busca una orden por su id
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    // Crea y persiste una nueva orden
    public Order createOrder(double totalPrice, StatusOrder status, Date createdAt, User user) {
        Order order = new Order();
        order.setTotalPrice(totalPrice);
        order.setStatus(status);
        order.setCreatedAt(createdAt != null ? createdAt : new Date());
        order.setUser(user);
        return orderRepository.save(order);
    }

    // Elimina una orden por id. Lanza excepción si no existe
    public void deleteOrder(Long id) throws OrderNotExistException {
        Optional<Order> existing = orderRepository.findById(id);
        if (existing.isEmpty()) {
            throw new OrderNotExistException();
        }
        orderRepository.deleteById(id);
    }

    // Edita una orden existente por id
    public Order editOrder(Long id, double newTotalPrice, StatusOrder newStatus, Date newCreatedAt, User newUser)
            throws OrderNotExistException {

        Optional<Order> existing = orderRepository.findById(id);
        if (existing.isEmpty()) {
            throw new OrderNotExistException();
        }

        Order order = existing.get();
        order.setTotalPrice(newTotalPrice);
        order.setStatus(newStatus);
        order.setCreatedAt(newCreatedAt != null ? newCreatedAt : order.getCreatedAt());
        order.setUser(newUser);
        return orderRepository.save(order);
    }

    // ==========================
    // Consultas de negocio útiles
    // ==========================

    // Lista órdenes de un usuario
    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    // Lista órdenes de un usuario, más recientes primero
    public List<Order> getRecentOrdersByUser(Long userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    // Filtra por estado
    public List<Order> getOrdersByStatus(StatusOrder status) {
        return orderRepository.findByStatus(status);
    }

    // Filtra por usuario y estado
    public List<Order> getOrdersByUserAndStatus(Long userId, StatusOrder status) {
        return orderRepository.findByUserIdAndStatus(userId, status);
    }

    // Filtra por rango de fechas (incluye ambos extremos)
    public List<Order> getOrdersBetween(Date from, Date to) {
        return orderRepository.findByCreatedAtBetween(from, to);
    }

    // ==========================
    // Operaciones específicas
    // ==========================

    // Actualiza solo el estado de una orden (query de escritura en repo)
    @Transactional
    public boolean updateOrderStatus(Long orderId, StatusOrder status) throws OrderNotExistException {
        if (orderRepository.findById(orderId).isEmpty()) {
            throw new OrderNotExistException();
        }
        int updated = orderRepository.updateOrderStatus(orderId, status);
        return updated > 0;
    }

    // Total gastado por un usuario en órdenes aprobadas
    public Double getTotalSpentByUser(Long userId) {
        return orderRepository.getTotalSpentByUser(userId);
    }
}