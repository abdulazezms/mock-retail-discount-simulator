package com.myretailbusiness.discountservice.service.product;

import com.myretailbusiness.discountservice.domain.Product;

public interface ProductService {
    Product findProductById(String id);
}
