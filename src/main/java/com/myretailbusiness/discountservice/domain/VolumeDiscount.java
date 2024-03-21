package com.myretailbusiness.discountservice.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "volume_discounts")
@Setter
@Getter
public class VolumeDiscount extends Discount  {
    private Double priceThreshold;
    private Double discountAmount;
    private Double derivedRate; // (discountAmount / priceThreshold)
}
