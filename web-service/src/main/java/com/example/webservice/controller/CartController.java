package com.example.webservice.controller;

import com.example.webservice.dto.ProductDto;
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
        // get the cart by id and display products and other info concerning the cart
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/add")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ProductDto addItemToCart(@RequestParam long cartId,
                                    @RequestParam long productId,
                                    @RequestParam long quantity) {
        //searches for cart in DB and for product in DB and then add the productId to map of
        return null;
    }

    @PostMapping("/remove")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void removeItemFromCart(@RequestParam long cartId,
                                   @RequestParam long productId) {
        //here you need to be cautious with how you remove it -> because the value if the map can be > 1

    }
}
