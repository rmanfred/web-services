package com.example.webservice.controller;

import com.example.webservice.dto.ProductDto;
import com.example.webservice.enums.Category;
import com.example.webservice.model.Review;
import com.example.webservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/search")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> getProductByName(@RequestParam String name) {
        return productService.getProductByName(name);
    }

    @GetMapping("/filter")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> getProductByFilter(@RequestParam(required = false) Category category,
                                               @RequestParam(required = false) Double priceUpLimit,
                                               @RequestParam(required = false) Double priceDownLimit) {
        return productService.getProductByFilter(category, priceUpLimit, priceDownLimit);
    }

    @PostMapping("/review")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void addProductReview(@RequestParam long productId,
                                 @RequestBody Review review) {
        productService.addProductReview(productId, review);
    }

    @GetMapping("/review")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Set<Review> getReviewsOfProduct(@RequestParam long productId) {
        return productService.getReviewsOfProduct(productId);
    }

//To get categories from the enum for filetring
    @GetMapping("/categories")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Set<String> getCategories() {
        return Arrays.stream(Category.values())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    @DeleteMapping("")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void deleteProductById(@RequestParam long productId) {
        productService.deleteProduct(productId);
    }

    @PostMapping("/add")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void addProduct(@RequestBody ProductDto productDto) {
        productService.addNewProduct(productDto);
    }
}
