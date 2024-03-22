package com.myretailbusiness.discountservice.service.bill;

import com.myretailbusiness.discountservice.domain.Product;

public interface ProductService {

    Product findProductById(String productId);
}
