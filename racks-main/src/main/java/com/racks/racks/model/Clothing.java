package com.racks.racks.model;

import jakarta.persistence.*;

@Entity
@Table(name = "clothing")
public class Clothing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private double price;
    private String size;

    private String status; // AVAILABLE or SOLD

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    // Constructors
    public Clothing() {}

    public Clothing(String title, double price, String size, User seller) {
        this.title = title;
        this.price = price;
        this.size = size;
        this.status = "AVAILABLE";
        this.seller = seller;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public User getSeller() { return seller; }
    public void setSeller(User seller) { this.seller = seller; }
}