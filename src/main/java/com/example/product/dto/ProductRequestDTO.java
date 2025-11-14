package com.example.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductRequestDTO {

    @NotBlank(message = "Product name cannot be empty")
    private String name;

    @Size(max = 255, message = "Description too long")
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;
}

