package com.myretailbusiness.discountservice.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "roles")
@Getter
@Setter
public class Role extends BaseEntity{
    @Indexed(useGeneratedName = true, unique = true)
    private String name;

    public Role(String name) {
        this.name = name;
    }
}
