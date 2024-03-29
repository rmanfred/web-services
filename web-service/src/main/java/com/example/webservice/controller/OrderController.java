package com.example.webservice.controller;

import com.example.webservice.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getOrderById(@RequestParam long id) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
