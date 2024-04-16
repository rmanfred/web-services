package com.example.webservice.controller;

import org.springframework.ui.Model;
import com.example.webservice.model.User;
import com.example.webservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.NoSuchElementException;

@Controller
public class WebController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";  // Returns home.html from the templates directory
    }

    @GetMapping("/user/register")
    public String signIn() {
        return "signUp";
    }

    @GetMapping("/welcome1")
    public String welcome1(@RequestParam("userId") Long userId, Model model) {
        try {
            User user = userService.findUserById(userId);
            model.addAttribute("firstName", user.getFirstName());
            model.addAttribute("lastName", user.getLastName());

//             Get the cart ID from the user or wherever it's stored
            Long cartId = user.getCartId(); // Assuming the cart ID is stored in the user object
            model.addAttribute("cartId", cartId);

            return "welcome1";
        } catch (NoSuchElementException e) {
            return "error"; // Redirect to an error page if user is not found
        }
    }


    @GetMapping("/welcome")
    public String welcome() {
        return "welcome"; // Returns welcome.html
    }

    @GetMapping("/user/log-in")
    public String logInUser() {
        return "logIn";
    }


}
