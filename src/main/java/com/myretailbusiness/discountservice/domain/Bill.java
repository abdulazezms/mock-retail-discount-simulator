package com.myretailbusiness.discountservice.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "bills")
@Setter
@Getter
public class Bill extends BaseEntity{
    private String userEmail;
    private List<BillItem> billItemList;
    private Double totalBeforeDiscount;
    private Double totalAfterDiscount;
    private DiscountType discountType;
    private String discountDescription;
    private Double discountRate;
    private Double discountAmount;
}