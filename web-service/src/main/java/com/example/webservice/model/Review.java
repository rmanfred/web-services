package com.example.webservice.model;

import com.example.webservice.enums.RatingCriteria;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    private RatingCriteria rating;
    private String comment;
}
