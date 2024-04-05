package com.example.webservice.controller;

import com.example.webservice.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getCartInfo(@RequestParam long id) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/add")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void addItemToCart(@RequestParam long cartId,
                              @RequestParam long productId) {

    }

    @PostMapping("/remove")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void removeItemFromCart(@RequestParam long cartId,
                                   @RequestParam long productId) {

    }
}
