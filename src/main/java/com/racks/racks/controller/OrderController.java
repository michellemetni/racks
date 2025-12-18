package com.racks.racks.controller;

import com.racks.racks.model.Order;
import com.racks.racks.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // Create a new order (buy clothing)
    @PostMapping
    public Order createOrder(@RequestParam long buyerId,
                             @RequestParam long clothingId) {
        return orderService.createOrder(buyerId, clothingId);
    }

    // Get all orders
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // Get order by ID
    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable long id) {
        return orderService.getOrderById(id);
    }
}
