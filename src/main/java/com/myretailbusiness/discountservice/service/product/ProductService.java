package com.myretailbusiness.discountservice.service.product;

import com.myretailbusiness.discountservice.domain.Product;
import com.myretailbusiness.discountservice.exception.NoResourceFoundException;
import com.myretailbusiness.discountservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product findProductById(String id) {
        return productRepository.findById(id).orElseThrow(() ->
                new NoResourceFoundException("Product with ID: " + id + " not found"));
    }
}
