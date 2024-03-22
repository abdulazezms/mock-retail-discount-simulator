package com.myretailbusiness.discountservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.myretailbusiness.discountservice.domain.Product;
@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
}
