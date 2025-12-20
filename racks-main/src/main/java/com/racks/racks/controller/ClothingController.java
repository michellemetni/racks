package com.racks.racks.controller;

import com.racks.racks.model.Clothing;
import com.racks.racks.service.ClothingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clothes")
@RequiredArgsConstructor
public class ClothingController {

    private final ClothingService clothingService;

    // Add new clothing
    @PostMapping
    public Clothing addClothing(@RequestParam String title,
                                @RequestParam double price,
                                @RequestParam String size,
                                @RequestParam long sellerId) {
        return clothingService.addClothing(title, price, size, sellerId);
    }

    // Get all available clothes
    @GetMapping
    public List<Clothing> getAvailableClothes() {
        return clothingService.getAvailableClothes();
    }

    // Get clothing by ID
    @GetMapping("/{id}")
    public Clothing getClothingById(@PathVariable long id) {
        return clothingService.getClothingById(id);
    }

    // Get clothes by seller
    @GetMapping("/seller/{sellerId}")
    public List<Clothing> getClothesBySeller(@PathVariable long sellerId) {
        return clothingService.getClothesBySeller(sellerId);
    }

    // Update clothing
    @PutMapping("/{id}")
    public Clothing updateClothing(@PathVariable long id,
                                   @RequestParam String title,
                                   @RequestParam double price,
                                   @RequestParam String size) {
        return clothingService.updateClothing(id, title, price, size);
    }

    // Delete clothing
    @DeleteMapping("/{id}")
    public void deleteClothing(@PathVariable long id) {
        clothingService.deleteClothing(id);
    }

    // Buy clothing
    @PostMapping("/{id}/buy")
    public Clothing buyClothing(@PathVariable long id,
                                @RequestParam long buyerId) {
        return clothingService.buyClothing(id, buyerId);
    }
}
