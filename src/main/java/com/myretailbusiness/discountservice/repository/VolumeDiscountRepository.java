package com.myretailbusiness.discountservice.repository;

import com.myretailbusiness.discountservice.domain.VolumeDiscount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolumeDiscountRepository extends MongoRepository<VolumeDiscount, String> {
}
