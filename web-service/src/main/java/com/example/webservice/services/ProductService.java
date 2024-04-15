package com.example.webservice.services;

import com.example.webservice.dto.ProductDto;
import com.example.webservice.enums.Category;
import com.example.webservice.model.Product;
import com.example.webservice.model.Review;
import com.example.webservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product productFindById (long id){
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new NoSuchElementException("Didn't find the product with this id: "+id);
        }
    }

    public List<ProductDto> getProductByName(String name) {
        List<Product> products = productRepository.findByTitleContainingIgnoreCase(name);
        return products.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    public List<ProductDto> getAllProducts() {
        var listOfProducts = productRepository.findAll();
        List<ProductDto> listOfDtos = new ArrayList<>();

        listOfProducts.forEach(pr -> listOfDtos.add(modelToDto(pr)));
        return listOfDtos;
    }

    public List<ProductDto> getProductByFilter(Category category, Double priceUpLimit,
                                               Double priceDownLimit) {
        var allProducts = getAllProducts();

        if (category != null) {
            allProducts = allProducts.stream().filter(pr -> pr.getCategory().equals(category))
                    .toList();
        }

        if (priceUpLimit != null) {
            allProducts = allProducts.stream().filter(pr -> pr.getPrice() <= priceUpLimit)
                    .toList();
        }

        if (priceDownLimit != null) {
            allProducts = allProducts.stream().filter(pr -> pr.getPrice() >= priceDownLimit)
                    .toList();
        }

        return allProducts;
    }

    public void addProductReview(long productId, Review review) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException(String.format("In DB there is no product with id %d", productId)));
        if (product.getReviews() == null) {
            product.setReviews(new HashSet<>());
        }
        product.getReviews().add(review);

        productRepository.save(product);
    }

    public Set<Review> getReviewsOfProduct(long productId) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException(String.format("In DB there is no product with id %d", productId)));
        return product.getReviews();
    }

    private ProductDto modelToDto(Product product) {
        var productDto = new ProductDto();

        productDto.setId(product.getId());
        productDto.setCategory(product.getCategory());
        productDto.setTitle(product.getTitle());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setCartId(product.getCart().getId());
        productDto.setStock(product.getStock());
        productDto.setReviews(product.getReviews());
        return productDto;
    }

    // Add a new product
    public void addNewProduct(){
        Product product = new Product();
        productRepository.save(product);
    }

    // Delete a product
    public void deleteProduct(long idProduct){
        productRepository.deleteById(idProduct);
    }

    //
}
