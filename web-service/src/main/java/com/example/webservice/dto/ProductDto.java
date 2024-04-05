package com.example.webservice.dto;

import com.example.webservice.enums.Category;
import com.example.webservice.model.Review;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDto {
    private Long id;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String title;
    private String description;
    private double price;
    private Long cartId;
    private long stock;
    private Set<Review> reviews;
}
