package com.epam.springboot.task1.controller;

import com.epam.springboot.task1.domain.Order;
import com.epam.springboot.task1.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestOrderController {

    private final OrderServiceImpl orderService;

    @Autowired
    public RestOrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/rest/order/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable int orderId) {
        Order order = orderService.findById(orderId);
        return ResponseEntity.ok().body(order);
    }

    @GetMapping("/rest/order")
    public List<Order> getAllOrders() {
        return orderService.findAll();
    }

    @PostMapping("/rest/order")
    public void createOrder(@RequestBody Order order) {
        orderService.add(order);
    }

    @DeleteMapping("/rest/order/{orderId}")
    public void deleteOrder(@PathVariable int orderId) {
        orderService.delete(orderId);
    }

}
