package com.example.webservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    private String city;
    private String country;
    private String street;
    private Byte streetNumber;
    // I guess user can have a multiple addresses and the default (or main) one should be identified
    private boolean isMain = true;

}
