package com.example.webservice.controller;

import com.example.webservice.dto.CartDto;
import com.example.webservice.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public CartDto getCartInfo(@RequestParam long id) {
        return cartService.getCartInfo(id);
    }

    @PostMapping("/add")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void addItemToCart(@RequestParam long cartId,
                                    @RequestParam long productId,
                                    @RequestParam long quantity) {
        cartService.addProductToCart(cartId, productId, quantity);
    }

    @PostMapping("/remove")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void removeItemFromCart(@RequestParam long cartId,
                                   @RequestParam long productId) {
        //here you need to be cautious with how you remove it -> because the value if the map can be > 1
        cartService.deleteProductFromCart(cartId, productId);
        // 1 by 1, if when remove one the total amount will decrease to 0, then remove this cart

    }

    @PostMapping("/totalPrice")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public double countTotalPrice(@RequestParam long cartId){
        return cartService.countTotalPrice(cartId);//This function will return a double value of totalPrice
    }

}
