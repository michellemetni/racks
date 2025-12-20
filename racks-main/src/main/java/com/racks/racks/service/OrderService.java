package com.racks.racks.service;

import com.racks.racks.model.Clothing;
import com.racks.racks.model.Order;
import com.racks.racks.model.User;
import com.racks.racks.repository.ClothingRepository;
import com.racks.racks.repository.OrderRepository;
import com.racks.racks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ClothingRepository clothingRepository;
    private final UserRepository userRepository;

    // Create a new order (buy clothing)
    public Order createOrder(long buyerId, long clothingId) {
        User buyer = userRepository.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        Clothing clothing = clothingRepository.findById(clothingId)
                .orElseThrow(() -> new RuntimeException("Clothing not found"));

        if (!"AVAILABLE".equals(clothing.getStatus())) {
            throw new RuntimeException("Clothing is not available");
        }

        // Mark clothing as sold
        clothing.setStatus("SOLD");
        clothingRepository.save(clothing);

        // Create and save order
        Order order = new Order(buyer, clothing);
        return orderRepository.save(order);
    }

    // Get all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Get order by ID
    public Order getOrderById(long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
