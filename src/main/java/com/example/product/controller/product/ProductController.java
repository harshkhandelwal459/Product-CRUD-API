package com.example.product.controller.product;

import com.example.product.dto.ProductRequestDTO;
import com.example.product.model.Product;
import com.example.product.response.ApiResponse;
import com.example.product.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Product CRUD API")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @Operation(
            summary = "Get all products",
            description = "Retrieve the list of all products. Returns 204 if no products are found."
    )
    public ResponseEntity<ApiResponse<List<Product>>> getProductList() {
        List<Product> products = productService.getProductList();

        if (products == null || products.isEmpty()) {
            ApiResponse<List<Product>> response = new ApiResponse<>(true,
                    HttpStatus.NO_CONTENT.value(),
                    "No products found",
                    null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }

        ApiResponse<List<Product>> response = new ApiResponse<>(true,
                HttpStatus.OK.value(),
                "Product list retrieved successfully",
                products);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get product by ID",
            description = "Retrieve a product by its ID. Returns 404 if the product does not exist."
    )
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);

        if (product == null) {
            ApiResponse<Product> response = new ApiResponse<>(false,
                    HttpStatus.NOT_FOUND.value(),
                    "Product not found",
                    null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        ApiResponse<Product> response = new ApiResponse<>(true,
                HttpStatus.OK.value(),
                "Product retrieved successfully",
                product);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(
            summary = "Create a product",
            description = "Create a new product with the provided details."
    )
    public ResponseEntity<ApiResponse<Product>> createProduct(@Valid @RequestBody ProductRequestDTO dto) {
        try {
            Product savedProduct = productService.createProduct(dto);

            ApiResponse<Product> response = new ApiResponse<>(true,
                    HttpStatus.CREATED.value(),
                    "Product created successfully",
                    savedProduct);

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            // Handle unexpected errors
            ApiResponse<Product> response = new ApiResponse<>(false,
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to create product: " + e.getMessage(),
                    null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update product",
            description = "Update an existing product by its ID. Returns 404 if the product does not exist."
    )
    public ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable Long id,
                                                              @Valid @RequestBody ProductRequestDTO dto) {
        Product updatedProduct = productService.updateProduct(id, dto);

        if (updatedProduct == null) {
            // Product not found
            ApiResponse<Product> response = new ApiResponse<>(false,
                    HttpStatus.NOT_FOUND.value(),
                    "Product not found",
                    null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        ApiResponse<Product> response = new ApiResponse<>(true,
                HttpStatus.OK.value(),
                "Product updated successfully",
                updatedProduct);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete product",
            description = "Delete a product by its ID. Returns 404 if the product does not exist."
    )
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);

        if (!deleted) {
            ApiResponse<Void> response = new ApiResponse<>(false,
                    HttpStatus.NOT_FOUND.value(),
                    "Product not found",
                    null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        ApiResponse<Void> response = new ApiResponse<>(true,
                HttpStatus.NO_CONTENT.value(),
                "Product deleted successfully",
                null);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

}
