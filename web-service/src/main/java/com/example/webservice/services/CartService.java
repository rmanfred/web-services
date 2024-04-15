package com.example.webservice.services;

import com.example.webservice.dto.CartDto;
import com.example.webservice.dto.ProductDto;
import com.example.webservice.model.Cart;
import com.example.webservice.model.Product;
import com.example.webservice.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductService productService;

    public CartDto getCartInfo(long id) {
        var cart = findCartById(id);

        var productIds = cart.getProducts();
        List<ProductDto> productDtoList = new ArrayList<>();
        for (Map.Entry<Long, Long> entry : productIds.entrySet()) {
            var productId = entry.getKey();
            var product = productService.productFindById(productId);
            var productDto = productService.modelToDto(product);
            productDtoList.add(productDto);
        }

        var cartDto = new CartDto();
        cartDto.setId(cart.getId());
        cartDto.setTotalPrice(countTotalPrice(id));
        cartDto.setProducts(productDtoList);

        return  cartDto;
    }

    public Cart findCartById(long id) {
        Optional<Cart> cart = cartRepository.findById(id);
        if (cart.isPresent()) {
            return cart.get();
        } else {
            throw new NoSuchElementException("Didn't find the cart with this id: " + id);
        }
    }

    // Delete one product from the cart by product id
    public void deleteProductFromCart(long idCart, long idProduct) {
        Cart cart = findCartById(idCart);
        Map<Long, Long> products = cart.getProducts();
        // Because when we try to delete one quantity of product from the cart
        // We must already have one , so no need to verify if product exist
        long newQuantity = products.get(idProduct) - 1;
        if (newQuantity > 0) {
            products.put(idProduct, newQuantity);
        } else {
            products.remove(idProduct);
        }
        cartRepository.save(cart);
    }

    // Add the product to the cart by quantity
    public void addProductToCart(long idCart, long idProduct, long quantityProductAdd) {
        Cart cart = findCartById(idCart);
        Map<Long, Long> products = cart.getProducts();
        // We need first check if the product already exist in the cart
        if (products.containsKey(idProduct)) {
            // if we have this product, we just add the quantity.
            long newQuantity = products.get(idProduct) + quantityProductAdd;
            products.replace(idProduct, newQuantity);
        } else {
            // If we don't have this product, we add a new product and its quantity to the Map
            products.put(idProduct, quantityProductAdd);
        }
        cartRepository.save(cart);
    }

    // Count total price, we assume that the initial total price equals to 0
    public double countTotalPrice(long cartId) {
        double totalPrice = 0;
        Cart cart = findCartById(cartId);
        Map<Long, Long> products = cart.getProducts();
        // if there is no products in the cart, total price = 0
        if (products.isEmpty()) {
            totalPrice = 0;
        } else {
            // We read the map, count the total price as SUM(quantity*price)
            for (Map.Entry<Long, Long> entry : products.entrySet()) {
                Long productId = entry.getKey();
                Long quantity = entry.getValue();
                Product product = productService.productFindById(productId);
                // We already count the total price, so the product is 100% exist.
                double priceProduct = product.getPrice();

                totalPrice += priceProduct * quantity;
            }
        }
        cartRepository.save(cart);
        return totalPrice;
    }


}
