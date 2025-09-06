package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Order;
import com.uade.tpo.demo.entity.OrderProduct;
import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.entity.dto.OrderProductRequest;
import com.uade.tpo.demo.entity.dto.OrderRequest;
import com.uade.tpo.demo.enums.StatusOrder;
import com.uade.tpo.demo.exceptions.InsufficientStockException;
import com.uade.tpo.demo.exceptions.OrderNotExistException;
import com.uade.tpo.demo.exceptions.ProductNotExistException;
import com.uade.tpo.demo.exceptions.UserNotFoundException;
import com.uade.tpo.demo.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;



@Service
public class OrderServiceImpl implements OrderService{
    
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private UserServiceImpl usersService;

    @Autowired
    private OrderProductServiceImpl orderProductService;


    
    public Optional<Order> getOrderById(Long id) throws OrderNotExistException{
        Optional<Order> o = orderRepository.findById(id);
        if(o.isEmpty()){
            throw new OrderNotExistException();
        }
        return o;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order createOrder(OrderRequest orderRequest) throws InsufficientStockException, ProductNotExistException, UserNotFoundException {
        double totalPrice = 0.0;
        List<OrderProduct> orderProducts = new ArrayList<>();

        for (OrderProductRequest orderProductRequest : orderRequest.getOrderProductRequest()) {
            Integer units = orderProductRequest.getUnits();
            Long productId = orderProductRequest.getProductId();

            Optional<Product> productOptional = productService.getProductById(productId);
            if (productOptional.isEmpty()) {
                throw new ProductNotExistException();
            }

            Product product = productOptional.get();

            if (product.getStock() < units) {
                throw new InsufficientStockException();
            }

            Double price = product.getPrice();

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProductId(productId);
            orderProduct.setPrice(price);
            orderProduct.setUnit(units);
            totalPrice += price * units;
            orderProducts.add(orderProduct);

            productService.reduceStock(productId, units);
        }

        Order order = new Order();
        order.setTotalPrice(totalPrice);
        
        order.setDate(new Date());
        order.setStatus(StatusOrder.APPROVED);

        //User Logica
        Optional<User> user = usersService.getUserById(orderRequest.getUserId());
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        order.setUser(user.get());

        // Persisitencia de los datos en la ordennnnn
        Order saveOrder =  orderRepository.save(order);
        for (OrderProduct orderProduct : orderProducts) {
            orderProduct.setOrder(saveOrder);
            orderProduct.setDate(new Date());
            orderProductService.createOrderProduct(orderProduct);
        }
        saveOrder.setOrderProducts(orderProducts);

        return saveOrder;
    }


    public Order deleteOrderById(Long id) throws OrderNotExistException {
    Optional<Order> order = orderRepository.findById(id);
    if (order.isEmpty()) {
        throw new OrderNotExistException();
    }

    Order o = order.get();
    o.setStatus(StatusOrder.DELETED);
    return orderRepository.save(o);
}
}