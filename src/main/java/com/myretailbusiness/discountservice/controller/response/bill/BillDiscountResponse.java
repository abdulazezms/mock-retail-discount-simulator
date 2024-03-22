package com.myretailbusiness.discountservice.controller.response.bill;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class BillDiscountResponse {
    private DiscountType discountType;
    private String discountDescription;
    private Double discountRate;
    private Double discountAmount;
}
