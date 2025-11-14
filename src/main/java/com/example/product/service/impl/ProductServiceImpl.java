package com.example.product.service.impl;

import com.example.product.dto.ProductRequestDTO;
import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;
import com.example.product.service.ProductService;
import com.example.product.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getProductList() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID " + id));
    }

    @Override
    public Product createProduct(ProductRequestDTO productRequestDTO) {
        return productRepository.save(mapToEntity(productRequestDTO));
    }

    @Override
    public Product updateProduct(Long id, ProductRequestDTO productRequestDTO) {
        Product existing = getProductById(id);
        existing.setName(productRequestDTO.getName());
        existing.setDescription(productRequestDTO.getDescription());
        existing.setPrice(productRequestDTO.getPrice());
        return productRepository.save(existing);
    }

    @Override
    public boolean deleteProduct(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }


    private Product mapToEntity(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        return product;
    }
}

