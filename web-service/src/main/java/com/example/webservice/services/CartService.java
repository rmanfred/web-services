package com.example.webservice.services;

import com.example.webservice.repository.CartRepository;
import com.example.webservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
}
