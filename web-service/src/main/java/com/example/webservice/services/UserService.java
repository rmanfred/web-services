package com.example.webservice.services;

import com.example.webservice.dto.UserDto;
import com.example.webservice.model.Cart;
import com.example.webservice.model.User;
import com.example.webservice.repository.CartRepository;
import com.example.webservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;

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
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setAddress(userDto.getAddress());
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
