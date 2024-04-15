package com.example.webservice.services;

import com.example.webservice.dto.CartDto;
import com.example.webservice.dto.OrderDto;
import com.example.webservice.model.Cart;
import com.example.webservice.model.Order;
import com.example.webservice.model.Product;
import com.example.webservice.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductService productService;

    public Cart findCartById(long id){
        Optional<Cart> cart = cartRepository.findById(id);
        if (cart.isPresent()) {
            return cart.get();
        }
        else {
            throw new NoSuchElementException("Didn't find the cart with this id: "+id);
        }
    }

    private CartDto modelToDto(Cart cart) {
        var cartDto = new CartDto();
        cartDto.setId(cart.getId());
        cartDto.setTotalPrice(cart.getTotalPrice());
        cartDto.setProducts();//This one need to change cart or cartDto?

        return cartDto;
    }

    // Add a cart
    public void createCart (){
        Cart cart = new Cart();
        cartRepository.save(cart);
    }

    // Delete a cart
    public void  deleteCart(long idCart){
        cartRepository.deleteById(idCart);
    }

    // Delete all one products from the cart
    public void cleanCartById(long idCart, long idProduct){
        Cart cart = findCartById(idCart);
        Map<Long, Long> products = cart.getProducts();
        products.remove(idProduct);
        cartRepository.save(cart);
    }

    // Delete one product from the cart by product id
    public void deleteProductFromCart(long idCart, long idProduct){
        Cart cart = findCartById(idCart);
        Map<Long, Long> products = cart.getProducts();
        // Because when we try to delete one quantity of product from the cart
        // We must already have one , so no need to verify if product exist
        long newQuantity = products.get(idProduct) - 1;
        if(newQuantity > 0){
            products.put(idProduct, newQuantity);
        }
        else {
            products.remove(idProduct);
        }
        cartRepository.save(cart);
    }

    // Add the product to the cart by quantity
    public void addProductToCart(long idCart, long idProduct, long quantityProductAdd){
        Cart cart = findCartById(idCart);
        Map<Long, Long> products = cart.getProducts();
        // We need first check if the product already exist in the cart
        if (products.containsKey(idProduct)){
            // if we have this product, we just add the quantity.
            long newQuantity = products.get(idProduct) + quantityProductAdd;
            products.replace(idProduct, newQuantity);
        }
        else {
            // If we don't have this product, we add a new product and its quantity to the Map
            products.put(idProduct, quantityProductAdd);
        }
        cartRepository.save(cart);
    }

    // Count total price, we assume that the initial total price equals to 0
    public double countTotalPrice(CartDto cartDto){
        double totalPrice = 0;
        Cart cart = findCartById(cartDto.getId());
        Map<Long, Long> products = cart.getProducts();
        // if there is no products in the cart, total price = 0
        if (products.isEmpty()){
            totalPrice = 0;
        }
        else {
            // We read the map, count the total price as SUM(quantity*price)
            for ( Map.Entry<Long, Long> entry : products.entrySet() ) {
                Long productId = entry.getKey();
                Long quantity = entry.getValue();
                Product product = productService.productFindById(productId);
                // We already counting the total price, so the product is 100% exist.
                double priceProduct = product.getPrice();

                totalPrice += priceProduct * quantity;
            }
        }
        cartRepository.save(cart);
        return totalPrice;
    }



}
