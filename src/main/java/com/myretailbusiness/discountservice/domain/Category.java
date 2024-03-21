package com.myretailbusiness.discountservice.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "categories")
@Setter
@Getter
public class Category extends BaseEntity{

    @Indexed(useGeneratedName = true, unique = true)
    private String name;
}