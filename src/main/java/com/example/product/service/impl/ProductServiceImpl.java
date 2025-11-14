package com.example.product.service.impl;

import com.example.product.dto.ProductRequestDTO;
import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;
import com.example.product.service.ProductService;
import com.example.product.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getProductList() {
        log.info("================ getProductList service start ================");
        List<Product> products = productRepository.findAll();
        log.info("================ getProductList service end ================");
        return products;
    }

    @Override
    public Product getProductById(Long id) {
        log.info("================ getProductById service start ================");
        log.info("Fetching product with ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID " + id));
        log.info("================ getProductById service end ================");
        return product;
    }

    @Override
    public Product createProduct(ProductRequestDTO productRequestDTO) {
        log.info("================ createProduct service start ================");
        log.info("Creating product: {}", productRequestDTO);
        Product product = productRepository.save(mapToEntity(productRequestDTO));
        log.info("================ createProduct service end ================");
        return product;
    }

    @Override
    public Product updateProduct(Long id, ProductRequestDTO productRequestDTO) {
        log.info("================ updateProduct service start ================");
        log.info("Updating product with ID: {}", id);
        Product existing = getProductById(id);
        existing.setName(productRequestDTO.getName());
        existing.setDescription(productRequestDTO.getDescription());
        existing.setPrice(productRequestDTO.getPrice());
        Product updated = productRepository.save(existing);
        log.info("================ updateProduct service end ================");
        return updated;
    }

    @Override
    public boolean deleteProduct(Long id) {
        log.info("================ deleteProduct service start ================");
        log.info("Deleting product with ID: {}", id);
        Optional<Product> productOptional = productRepository.findById(id);
        boolean deleted = false;
        if (productOptional.isPresent()) {
            productRepository.deleteById(id);
            deleted = true;
        }
        log.info("================ deleteProduct service end ================");
        return deleted;
    }

    private Product mapToEntity(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        return product;
    }
}
