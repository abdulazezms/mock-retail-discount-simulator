package com.myretailbusiness.discountservice.controller.body.bill;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillItemBody {
    private String productId;
    private Integer quantity;
}
