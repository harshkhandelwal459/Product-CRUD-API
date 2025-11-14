package com.example.product.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProductRequestDTO {

    @NotBlank(message = "Product name cannot be empty")
    private String name;

    @Size(max = 255, message = "Description too long")
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private Double price;
}

