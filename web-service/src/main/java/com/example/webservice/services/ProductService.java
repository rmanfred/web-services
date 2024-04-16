package com.example.webservice.services;

import com.example.webservice.dto.ProductDto;
import com.example.webservice.enums.Category;
import com.example.webservice.model.Product;
import com.example.webservice.model.Review;
import com.example.webservice.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @PostConstruct
    public void initializeProducts() {
        List<Product> products = new ArrayList<>();
        var product1 = new Product(Category.SHOES, "Adidas Samba", "Cool shoes", 120, 35);
        var product2 = new Product(Category.SHOES, "New Balance", "Classical", 180, 30);
        var product3 = new Product(Category.SHOES, "Nike Air Max", "Nike is Nike", 140, 20);
        var product4 = new Product(Category.SHOES, "Veja", "French shoes", 150, 10);
        var product5 = new Product(Category.T_SHIRTS, "U.S. Polo", "Classical", 100, 90);
        var product6 = new Product(Category.T_SHIRTS, "Lacoste", "Fancy", 110, 180);
        var product7 = new Product(Category.T_SHIRTS, "Tommy Hilfiger", "As everybody else", 80, 40);
        var product8 = new Product(Category.T_SHIRTS, "Massimo Dutti", "Nice", 90, 100);
        var product9 = new Product(Category.GLOVES, "Prada", "Too expensive", 350, 10);
        var product10 = new Product(Category.GLOVES, "LV", "Trop expensive", 400, 10);
        var product11 = new Product(Category.GLOVES, "Loewe", "Extra expensive", 300, 20);
        var product12 = new Product(Category.SCARFS, "MadeUp", "Scarf 1", 60, 10);
        var product13 = new Product(Category.SCARFS, "MakeUp", "Scarf 2", 90, 10);
        var product14 = new Product(Category.SCARFS, "Furla", "Scarf 3", 70, 20);
        var product15 = new Product(Category.PANTS, "Pants1", "Pants 1", 100, 90);
        var product16 = new Product(Category.PANTS, "Pants2", "Pants 2", 110, 180);
        var product17 = new Product(Category.PANTS, "Pants3", "Pants 3", 50, 40);
        var product18 = new Product(Category.PANTS, "Uniqlo", "Uniqlo pants", 80, 100);
        var product19 = new Product(Category.BACKPACKS, "BackPack1", "BackPack 1", 20, 90);
        var product20 = new Product(Category.BACKPACKS, "BackPack2", "BackPack 2", 30, 180);
        var product21 = new Product(Category.BACKPACKS, "BackPack3", "BackPack 3", 40, 40);
        var product22 = new Product(Category.BACKPACKS, "BackPack4", "BackPack 4", 50, 100);

        var existingProducts = productRepository.findAll();
        if (existingProducts.isEmpty()) {
            productRepository.saveAll(List.of(product1, product2, product3, product4, product5, product6, product7, product8,
                    product9, product10, product11, product12, product13, product14, product15, product16, product17, product18,
                    product19, product20, product21, product22));
        }
    }

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

    public ProductDto modelToDto(Product product) {
        var productDto = new ProductDto();

        productDto.setId(product.getId());
        productDto.setCategory(product.getCategory());
        productDto.setTitle(product.getTitle());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
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
