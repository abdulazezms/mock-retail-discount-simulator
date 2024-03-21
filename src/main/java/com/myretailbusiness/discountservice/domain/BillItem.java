package com.myretailbusiness.discountservice.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillItem {
    private Product product;
    private Integer quantity;
}
