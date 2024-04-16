package com.example.webservice.services;

import com.example.webservice.dto.OrderDto;
import com.example.webservice.dto.ProductDto;
import com.example.webservice.enums.OrderStatus;
import com.example.webservice.model.Order;
import com.example.webservice.repository.CartRepository;
import com.example.webservice.repository.OrderRepository;
import com.example.webservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductService productService;

    public Order findOrderById (long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return order.get();
        } else {
            throw new NoSuchElementException("Didn't find the order with this id: " + id);
        }
    }

    public OrderDto getOrderInfo(long id){
        var order = findOrderById(id);
        return modelToDto(order);
    }

    private OrderDto modelToDto(Order order) {
        var orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setTotalPrice(order.getTotalPrice());
        orderDto.setPaymentMethod(order.getPaymentMethod());
        orderDto.setUserId(order.getUser().getId());

        var user = userRepository.findById(order.getUser().getId())
                .orElseThrow(() -> new RuntimeException(String.format("In DB there is no user with id %d", orderDto.getUserId())));

        var cart = cartRepository.findById(order.getUser().getCartId())
                .orElseThrow(() -> new RuntimeException(String.format("In DB there is no cart with id %d", order.getUser().getCartId())));

        Map<ProductDto, Long> products = new HashMap<>();
        for (Map.Entry<Long, Long> entry : cart.getProducts().entrySet()) {
            var prId = entry.getKey();
            var product = productService.productFindById(prId);
            products.put(productService.modelToDto(product), entry.getValue());
        }
        orderDto.setProducts(products);
        orderDto.setAddress(user.getAddress());

        return orderDto;
    }

    public long placeOrder(OrderDto orderDto) {
        var user = userRepository.findById(orderDto.getUserId())
                .orElseThrow(() -> new RuntimeException(String.format("In DB there is no user with id %d", orderDto.getUserId())));
        var order = new Order();
        order.setUser(user);
        order.setTotalPrice(orderDto.getTotalPrice());
        order.setOrderStatus(OrderStatus.CREATED);
        order.setPaymentMethod(orderDto.getPaymentMethod());

        var cart = cartRepository.findById(order.getUser().getCartId())
                .orElseThrow(() -> new RuntimeException(String.format("In DB there is no cart with id %d", user.getCartId())));
        order.setProducts(new HashSet<>(cart.getProducts().values()));

        order = orderRepository.save(order);
        return order.getId();
    }
}
