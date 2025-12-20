package com.racks.racks.service;

import com.racks.racks.model.Clothing;
import com.racks.racks.model.User;
import com.racks.racks.model.Order;
import com.racks.racks.repository.ClothingRepository;
import com.racks.racks.repository.UserRepository;
import com.racks.racks.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClothingService {

    private final ClothingRepository clothingRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    // Add new clothing
    public Clothing addClothing(String title, double price, String size, long sellerId) {
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        Clothing clothing = new Clothing(title, price, size, seller);
        return clothingRepository.save(clothing);
    }

    // Get all available clothes
    public List<Clothing> getAvailableClothes() {
        return clothingRepository.findByStatus("AVAILABLE");
    }

    // Get clothing by ID
    public Clothing getClothingById(long id) {
        return clothingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clothing not found"));
    }

    // Get clothes by seller
    public List<Clothing> getClothesBySeller(long sellerId) {
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));
        return clothingRepository.findAll()
                .stream()
                .filter(c -> c.getSeller().getId().equals(sellerId))
                .toList();
    }

    // Update clothing
    public Clothing updateClothing(long id, String title, double price, String size) {
        Clothing clothing = getClothingById(id);
        clothing.setTitle(title);
        clothing.setPrice(price);
        clothing.setSize(size);
        return clothingRepository.save(clothing);
    }

    // Delete clothing
    public void deleteClothing(long id) {
        clothingRepository.deleteById(id);
    }

    // Buy clothing
    public Clothing buyClothing(long clothingId, long buyerId) {
        Clothing clothing = getClothingById(clothingId);

        if (!"AVAILABLE".equals(clothing.getStatus())) {
            throw new RuntimeException("Clothing is not available");
        }

        User buyer = userRepository.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        clothing.setStatus("SOLD");
        clothingRepository.save(clothing);

        Order order = new Order(buyer, clothing);
        orderRepository.save(order);

        return clothing;
    }
}
