package com.example.webservice.controller;

import com.example.webservice.dto.ProductDto;
import com.example.webservice.enums.Category;
import com.example.webservice.model.Review;
import com.example.webservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> getAllProducts() {
        return List.of();
    }

    @GetMapping("/filter")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> getProductByFilter(@RequestParam(required = false) Category category,
                                               @RequestParam(required = false) double priceUpLimit,
                                               @RequestParam(required = false) double priceDownLimit) {
        return List.of();
    }

    @PostMapping("/review")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void addProductReview(@RequestBody Review review) {

    }

}
