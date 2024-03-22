package com.myretailbusiness.discountservice.controller.response.bill;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class BillDiscountResponse implements Comparable<BillDiscountResponse> {
    private String discountType;
    private String discountDescription;
    private Double discountRate;
    private Double discountAmount;
    @Override
    public int compareTo(BillDiscountResponse other) {
        return Double.compare(other.discountAmount, this.discountAmount); // Descending order by discount amount
    }
}
