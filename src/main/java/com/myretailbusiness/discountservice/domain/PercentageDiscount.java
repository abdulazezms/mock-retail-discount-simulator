package com.myretailbusiness.discountservice.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "percentage_discounts")
@Setter
@Getter
public class PercentageDiscount extends Discount {
    private Double discountRate; // e.g., 0.1 for 10%
}
