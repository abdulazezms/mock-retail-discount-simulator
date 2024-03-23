package com.myretailbusiness.discountservice.service.product;

import com.myretailbusiness.discountservice.domain.Product;
import com.myretailbusiness.discountservice.exception.NoResourceFoundException;
import com.myretailbusiness.discountservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product findProductById(String id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new NoResourceFoundException("Product with ID: " + id + " not found"));
        return product;
    }
}
