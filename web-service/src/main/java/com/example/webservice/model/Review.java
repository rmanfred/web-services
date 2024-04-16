package com.example.webservice.model;

import com.example.webservice.enums.RatingCriteria;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public @Data class Review {
    @Enumerated(EnumType.STRING)
    private RatingCriteria rating;
    private String comment;
}
