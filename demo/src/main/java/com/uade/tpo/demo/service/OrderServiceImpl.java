///package com.uade.tpo.demo.service;
///
///import com.uade.tpo.demo.entity.Order;
///import com.uade.tpo.demo.entity.User;
///import com.uade.tpo.demo.enums.StatusOrder;
///import com.uade.tpo.demo.exceptions.OrderNotExistException;
///import com.uade.tpo.demo.repository.OrderRepository;
///
///import org.springframework.beans.factory.annotation.Autowired;
///import org.springframework.stereotype.Service;
///
///import java.util.Date;
///import java.util.List;
///import java.util.Optional;
///
///@Service
///public class OrderServiceImpl {
///
///    @Autowired
///    private OrderRepository orderRepository;
///
///    // Listar todos los pedidos
///    public List<Order> getOrders() {
///        return orderRepository.findAll();
///    }
///
///    // Buscar un pedido por ID
///    public Optional<Order> getOrderById(Long id) {
///        return orderRepository.findById(id);
///    }
///
///    // Crear un pedido
///    public Order createOrder(double totalPrice, StatusOrder status, Date createdAt, User user) {
///        Order order = new Order();
///        order.setTotalPrice(totalPrice);
///        order.setStatus(status);
///        order.setCreatedAt(createdAt != null ? createdAt : new Date());
///        order.setUser(user);
///        return orderRepository.save(order);
///    }
///
///    // Eliminar un pedido por ID
///    public void deleteOrder(long id) throws OrderNotExistException {
///        Optional<Order> order = orderRepository.findById(id);
///        if (order.isEmpty()) {
///            throw new OrderNotExistException();
///        }
///        orderRepository.deleteById(id);
///    }
///
///    // Editar un pedido por ID
///    public Order editOrder(Long id, double newTotalPrice, StatusOrder newStatus, Date newCreatedAt, User newUser)
///            throws OrderNotExistException {
///
///        Optional<Order> o = orderRepository.findById(id);
///        if (o.isEmpty()) {
///            throw new OrderNotExistException();
///        }
///
///        Order order = o.get();
///        order.setTotalPrice(newTotalPrice);
///        order.setStatus(newStatus);
///        order.setCreatedAt(newCreatedAt != null ? newCreatedAt : order.getCreatedAt());
///        order.setUser(newUser);
///
///        return orderRepository.save(order);
///    }
///}