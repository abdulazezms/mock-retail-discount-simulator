package com.myretailbusiness.discountservice.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
@Getter
@Setter
public class Product extends BaseEntity{
    @DBRef
    private Category category;
    private String name;
    private Double price;
}
