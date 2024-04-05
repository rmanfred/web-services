package com.example.webservice.services;

import com.example.webservice.model.Cart;
import com.example.webservice.model.Product;
import com.example.webservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product productFindById (long id){
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        }
        else {
            throw new NoSuchElementException("Didn't find the product with this id: "+id);
        }
    }
}
