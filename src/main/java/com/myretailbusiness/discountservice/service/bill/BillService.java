package com.myretailbusiness.discountservice.service.bill;

import com.myretailbusiness.discountservice.controller.body.bill.BillBody;
import com.myretailbusiness.discountservice.controller.response.bill.BillResponse;

public interface BillService {
    BillResponse createBill(BillBody billBody);
}
