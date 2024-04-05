package com.example.webservice.dto;

import com.example.webservice.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private Address address;
    private List<OrderDto> orders;
    private Long cartId; // created once the user created and needs to store there
}
