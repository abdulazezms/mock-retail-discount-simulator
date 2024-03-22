package com.myretailbusiness.discountservice.repository;


import com.myretailbusiness.discountservice.domain.Bill;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BillRepository extends MongoRepository<Bill,String> {
}
