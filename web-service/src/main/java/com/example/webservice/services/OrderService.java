package com.example.webservice.services;

import com.example.webservice.dto.OrderDto;
import com.example.webservice.enums.OrderStatus;
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
    @Autowired
    private UserRepository userRepository;

    public Order findOrderById (long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return order.get();
        } else {
            throw new NoSuchElementException("Didn't find the order with this id: " + id);
        }
    }

    public OrderDto getOrderInfo(long id){
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("In DB there is no order with id %d", id)));
        return modelToDto(order);
    }

    private OrderDto modelToDto(Order order) {
        var orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setTotalPrice(order.getTotalPrice());
        orderDto.setPaymentMethod(order.getPaymentMethod());
        orderDto.setUserId(order.getUser().getId());

        return orderDto;
    }

    public long placeOrder(OrderDto orderDto, long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(String.format("In DB there is no user with id %d", userId)));
        var order = new Order();
        order.setUser(user);
        order.setTotalPrice(orderDto.getTotalPrice());
        order.setOrderStatus(OrderStatus.CREATED);
        order.setPaymentMethod(orderDto.getPaymentMethod());

        order = orderRepository.save(order);
        return order.getId();
    }
}
