package com.myretailbusiness.discountservice.controller.response.bill;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillItemResponse {
    private String product;
    private Integer quantity;
}
