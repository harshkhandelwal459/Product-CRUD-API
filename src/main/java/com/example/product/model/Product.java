package com.example.product.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name cannot be blank")
    @Size(min = 2, max = 100, message = "Product name must be 2–100 characters long")
    private String name;

    @Size(max = 255, message = "Description too long")
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;
}

