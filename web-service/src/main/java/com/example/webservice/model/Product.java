package com.example.webservice.model;

import com.example.webservice.enums.Category;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String title;
    private String description;
    private double price;
    private long stock; // validation -> if enough stock for the product
    @ElementCollection
    @CollectionTable(name = "reviews", joinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")})
    @Column(name = "reviews")
    private Set<Review> reviews;

    public Product() {}

    public Product(Category category, String title, String description,
                   double price, long stock) {
        this.category = category;
        this.title = title;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    //price, discounts, size -> picture?

}
