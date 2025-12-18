package com.racks.racks.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @OneToOne
    @JoinColumn(name = "clothing_id", unique = true)
    private Clothing clothing;

    private LocalDateTime purchaseDate;

    // Constructors
    public Order() {}

    public Order(User buyer, Clothing clothing) {
        this.buyer = buyer;
        this.clothing = clothing;
        this.purchaseDate = LocalDateTime.now();
    }

    // Getters & Setters
    public Long getId() { return id; }

    public User getBuyer() { return buyer; }
    public void setBuyer(User buyer) { this.buyer = buyer; }

    public Clothing getClothing() { return clothing; }
    public void setClothing(Clothing clothing) { this.clothing = clothing; }

    public LocalDateTime getPurchaseDate() { return purchaseDate; }
}