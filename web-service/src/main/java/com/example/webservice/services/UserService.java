package com.example.webservice.services;

import com.example.webservice.dto.UserDto;
import com.example.webservice.model.Address;
import com.example.webservice.model.Cart;
import com.example.webservice.model.Order;
import com.example.webservice.model.User;
import com.example.webservice.repository.CartRepository;
import com.example.webservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private OrderService orderService;

    public User findUserById(long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        else {
            throw new NoSuchElementException("Didn't find the user with this id: "+id);
        }
    }

    // Add a new user. return id of created user
    public long registerUser (UserDto userDto){
        User user = dtoToModelBasic(userDto);

        var cart = new Cart();
        cart = cartRepository.save(cart);

        user.setCartId(cart.getId());
        user = userRepository.save(user);

        return user.getId();
    }

//    public long logInUser(String userName, String password) {
//        var user = userRepository.findByUsernameAndPassword(userName, password)
//                .orElseThrow(() -> new RuntimeException(String.format("In DB there is no user with userName %d", userName)));
//
//        return user.getCartId();
//    }
    public long logInUser(String userName, String password) {
        try {
            var user = userRepository.findByUsernameAndPassword(userName, password)
                    .orElseThrow(() -> new RuntimeException("No user found with provided credentials"));

            return user.getCartId();
        } catch (Exception e) {
            // Log the exception details to help with debugging
            System.out.println("Failed to log in: " + e.getMessage());
            throw e;  // Re-throwing the exception or handle it appropriately
        }
    }


    // Delete a user
    public void deleteUser(long idUser){
        userRepository.deleteById(idUser);
    }

    // Update first name
    public void updateUserInfo(UserDto userDto){
        User user = findUserById(userDto.getId());
        user.setUsername(user.getUsername());
        user.setLastName(user.getLastName());
        user.setPassword(user.getPassword());
        user.setEmail(user.getEmail());
        user.setAddress(user.getAddress());
        userRepository.save(user);
    }

    // Update last name
    public void updateLastName(long idUser, String newLastName){
        User user = findUserById(idUser);
        user.setLastName(newLastName);
        userRepository.save(user);
    }

    // Update email
    public void updateEmail(long idUser, String newEmail){
        User user = findUserById(idUser);
        user.setEmail(newEmail);
        userRepository.save(user);
    }

    // Update userName
    public void updateUsername(long idUser, String newUserName){
        User user = findUserById(idUser);
        user.setUsername(newUserName);
        userRepository.save(user);
    }

    // Update password
    public void updatePassword(long idUser, String newPassword){
        User user = findUserById(idUser);
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    // Update Address
    public void updateAddress(long idUser, Address newAddress){
        User user = findUserById(idUser);
        user.setAddress(newAddress);
        userRepository.save(user);
    }

    // Add a new order
    public void addNewOrder(long userId, long idOrder){
        User user = findUserById(userId);
        Order order = orderService.findOrderById(idOrder);
        List<Order> orders = user.getOrders();
        orders.add(order);
        user.setOrders(orders);
        userRepository.save(user);
    }

    // Delete an order from the list orders
    public void deleteAnOrder(long userId, long idOrder){
        User user = findUserById(userId);
        Order order = orderService.findOrderById(idOrder);
        // Because when we delete an order, this order must be in the list, so no need to check the existence
        List<Order> orders = user.getOrders();
        orders.remove(order);
        user.setOrders(orders);
        userRepository.save(user);
    }

    // Delete all orders
    public void cleanOrderList (long idUser){
        User user = findUserById(idUser);
        List<Order> orders = user.getOrders();
        orders.clear();
        user.setOrders(orders);
        userRepository.save(user);
    }

    private User dtoToModelBasic(UserDto userDto) {
        var user = new User();

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());

        return user;
    }
}
