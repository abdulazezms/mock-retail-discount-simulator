package com.myretailbusiness.discountservice.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "percentage_discounts")
@Setter
@Getter
@ToString
public class PercentageDiscount extends Discount {
    private Double discountRate; // e.g., 0.1 for 10%
}
