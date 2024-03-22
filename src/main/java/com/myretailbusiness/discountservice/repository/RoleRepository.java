package com.myretailbusiness.discountservice.repository;


import com.myretailbusiness.discountservice.domain.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {
    boolean existsByName(String role);

    Optional<Role> findByName(String name);
}
