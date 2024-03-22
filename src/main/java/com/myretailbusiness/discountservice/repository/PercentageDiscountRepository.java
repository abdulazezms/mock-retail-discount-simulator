package com.myretailbusiness.discountservice.repository;

import com.myretailbusiness.discountservice.domain.PercentageDiscount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PercentageDiscountRepository extends MongoRepository<PercentageDiscount, String> {

}
