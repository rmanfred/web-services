package com.example.webservice.services;

import com.example.webservice.model.Cart;
import com.example.webservice.model.Order;
import com.example.webservice.repository.OrderRepository;
import com.example.webservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order findOrderById (long id){
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return order.get();
        }
        else {
            throw new NoSuchElementException("Didn't find the order with this id: "+id);
        }
    }
}
