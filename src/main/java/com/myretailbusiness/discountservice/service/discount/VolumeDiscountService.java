package com.myretailbusiness.discountservice.service.discount;

import com.myretailbusiness.discountservice.controller.response.bill.BillResponse;
import com.myretailbusiness.discountservice.domain.Bill;
import com.myretailbusiness.discountservice.domain.VolumeDiscount;

public interface VolumeDiscountService extends DiscountStrategy{
    BillResponse calculateDiscount(Bill bill, VolumeDiscount volumeDiscount);
}
