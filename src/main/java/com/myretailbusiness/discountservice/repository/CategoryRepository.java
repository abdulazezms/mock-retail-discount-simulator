package com.myretailbusiness.discountservice.repository;


import com.myretailbusiness.discountservice.domain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    Optional<Category> findByName(String name);
}
