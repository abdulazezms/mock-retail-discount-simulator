package com.myretailbusiness.discountservice.repository;

import com.myretailbusiness.discountservice.domain.PercentageDiscount;
import com.myretailbusiness.discountservice.domain.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PercentageDiscountRepository extends MongoRepository<PercentageDiscount, String> {
    default List<PercentageDiscount> findApplicableDiscounts(List<Role> userRoles, Integer yearsSinceUserRegistration) {
        return findByRolesApplicableIsContainingAndMinimumTenureInYearsIsLessThanEqualAndIsActive(userRoles, yearsSinceUserRegistration, true);
    }

    List<PercentageDiscount> findByRolesApplicableIsContainingAndMinimumTenureInYearsIsLessThanEqualAndIsActive
            (List<Role> rolesApplicable, Integer yearsSinceUserRegistration, Boolean isActive);
}
