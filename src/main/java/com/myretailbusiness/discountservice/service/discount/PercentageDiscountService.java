package com.myretailbusiness.discountservice.service.discount;

import com.myretailbusiness.discountservice.controller.response.bill.BillDiscountResponse;
import com.myretailbusiness.discountservice.domain.Bill;
import com.myretailbusiness.discountservice.domain.PercentageDiscount;

public interface PercentageDiscountService extends DiscountStrategy{
    BillDiscountResponse calculateDiscount(Bill bill, PercentageDiscount percentageDiscount);
}
