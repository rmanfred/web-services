package com.example.webservice.controller;

import com.example.webservice.dto.UserDto;
import com.example.webservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public long registerUser(@RequestBody UserDto userDto) {
        return userService.registerUser(userDto);
    }

    // return the cart id of the user
    @PostMapping("/log-in")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public long logInUser(@RequestParam String userName,
                          @RequestParam String password) {
        return userService.logInUser(userName, password);
    }

    @PostMapping("/update-info")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void updateUserInfo(@RequestBody UserDto userDto) {
        userService.updateUserInfo(userDto);
    }

}
