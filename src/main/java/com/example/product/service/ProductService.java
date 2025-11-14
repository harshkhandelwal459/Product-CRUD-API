package com.example.product.service;

import com.example.product.dto.ProductRequestDTO;
import com.example.product.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProductList();
    Product getProductById(Long id);
    Product createProduct(ProductRequestDTO productRequestDTO);
    Product updateProduct(Long id, ProductRequestDTO productRequestDTO);
    boolean deleteProduct(Long id);
}
