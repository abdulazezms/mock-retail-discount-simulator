package com.myretailbusiness.discountservice.domain;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Setter
@Getter
public abstract class Discount extends BaseEntity{

    private String description;

    private Boolean isActive;

    @DBRef
    private List<Role> rolesApplicable;

    @DBRef
    private List<Category> categoriesNotApplicable;

    private Integer minimumTenureInYears;

    public boolean isCategoryNotApplicable(Category category) {
        if(this.categoriesNotApplicable == null) return false;
        for(Category categoryNotApplicable : categoriesNotApplicable)
            if(categoryNotApplicable.getId().equals(category.getId()))
                return true;
        return false;
    }
}
