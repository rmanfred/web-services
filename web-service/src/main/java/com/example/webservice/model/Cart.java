package com.example.webservice.model;

import jakarta.persistence.*;

import java.util.Map;

@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "chosen_products",
            joinColumns = {@JoinColumn(name = "cart_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<Long, Long> products;

    private double totalPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<Long, Long> getProducts() {
        return products;
    }

    public void setProducts(Map<Long, Long> products) {
        this.products = products;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
