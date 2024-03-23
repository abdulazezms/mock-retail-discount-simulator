package com.myretailbusiness.discountservice.service.discount;

import com.myretailbusiness.discountservice.controller.response.bill.BillResponse;
import com.myretailbusiness.discountservice.domain.Bill;

public interface DiscountStrategy {

    /**
     *
     * @param bill The bill of the user
     * @return optimal discount for the user on this bill, if applicable.
     */
    BillResponse getBillAfterOptimalDiscount(Bill bill);

}
