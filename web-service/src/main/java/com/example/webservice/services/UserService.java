package com.example.webservice.services;

import com.example.webservice.model.Address;
import com.example.webservice.model.Cart;
import com.example.webservice.model.Order;
import com.example.webservice.model.User;
import com.example.webservice.repository.OrderRepository;
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
    private OrderService orderService;

    public User findUserById (long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        else {
            throw new NoSuchElementException("Didn't find the user with this id: "+id);
        }
    }

    // Add a new user
    public void addUser (){
        User user = new User();
        userRepository.save(user);
    }

    // Delete a user
    public void deleteUser(long idUser){
        userRepository.deleteById(idUser);
    }

    // Update first name
    public void updateFirstName(long idUser, String newFirstName){
        User user = findUserById(idUser);
        user.setFirstName(newFirstName);
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

    // User login
    public User userLogin(String userName, String password){
        Optional<User> user = userRepository.findByUsernameAndPassword(userName, password);
        if (user.isPresent()) {
            return user.get();
        }
        else {
            throw new NoSuchElementException("Didn't find the user when check login");
        }
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
}
