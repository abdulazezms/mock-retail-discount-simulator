package com.myretailbusiness.discountservice.controller.response.bill;

import com.myretailbusiness.discountservice.controller.response.BaseEntityResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillResponse extends BaseEntityResponse {
    private Double totalBeforeDiscount;
    private Double totalAfterDiscount;
    private String discountType;
    private String discountDescription;
    private Double discountRate;
    private Double discountAmount;
}
