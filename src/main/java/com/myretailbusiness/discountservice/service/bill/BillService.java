package com.myretailbusiness.discountservice.service.bill;

import com.myretailbusiness.discountservice.controller.body.bill.BillBody;
import com.myretailbusiness.discountservice.controller.response.bill.BillDiscountResponse;

public interface BillService {
    BillDiscountResponse createBill(BillBody billBody);
}
