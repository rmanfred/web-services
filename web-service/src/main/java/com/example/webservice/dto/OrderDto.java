package com.example.webservice.dto;

import com.example.webservice.enums.PaymentMethod;
import com.example.webservice.model.Address;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDto {
    private Long id;
    private Long userId;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    private double totalPrice;
    private Map<ProductDto, Long> products;
    private Address address;
}
