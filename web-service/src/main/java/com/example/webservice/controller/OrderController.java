package com.example.webservice.controller;

import com.example.webservice.dto.OrderDto;
import com.example.webservice.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public OrderDto getOrderInfo(@RequestParam long id) { //or maybe also add request of all orders of the user
        return orderService.getOrderInfo(id);
    }

    @PostMapping("/place")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public long placeOrder(@RequestBody OrderDto orderDto) {
        return orderService.placeOrder(orderDto);
    }

}
