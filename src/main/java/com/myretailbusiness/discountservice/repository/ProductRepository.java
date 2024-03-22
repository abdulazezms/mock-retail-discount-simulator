package com.myretailbusiness.discountservice.repository;

import com.myretailbusiness.discountservice.domain.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
}
