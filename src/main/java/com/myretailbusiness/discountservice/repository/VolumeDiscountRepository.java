package com.myretailbusiness.discountservice.repository;

import com.myretailbusiness.discountservice.domain.Role;
import com.myretailbusiness.discountservice.domain.VolumeDiscount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VolumeDiscountRepository extends MongoRepository<VolumeDiscount, String> {
    default List<VolumeDiscount> findApplicableDiscounts(List<Role> userRoles, int yearsSinceUserRegistration) {
        return findByRolesApplicableIsContainingAndMinimumTenureInYearsIsLessThanEqualAndIsActive(userRoles, yearsSinceUserRegistration, true);
    }

    List<VolumeDiscount> findByRolesApplicableIsContainingAndMinimumTenureInYearsIsLessThanEqualAndIsActive
            (List<Role> rolesApplicable, Integer yearsSinceUserRegistration, Boolean isActive);
}
