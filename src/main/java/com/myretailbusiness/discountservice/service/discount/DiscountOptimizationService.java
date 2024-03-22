package com.myretailbusiness.discountservice.service.discount;

import com.myretailbusiness.discountservice.controller.response.bill.BillDiscountResponse;
import com.myretailbusiness.discountservice.domain.Bill;
import com.myretailbusiness.discountservice.domain.DiscountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class DiscountOptimizationService {
    private List<DiscountStrategy> discountStrategyList;

    @Autowired
    public DiscountOptimizationService(List<DiscountStrategy> discountStrategyList) {
        this.discountStrategyList = discountStrategyList;
    }

    public Bill applyOptimalDiscount(Bill bill) {
        if(discountStrategyList.isEmpty()) return bill;
        List<BillDiscountResponse> candidateOptimalDiscount = new ArrayList<>();
        for(DiscountStrategy discountStrategy : discountStrategyList) {
            candidateOptimalDiscount.add(discountStrategy.getOptimalDiscountInfo(bill));
        }
        Collections.sort(candidateOptimalDiscount);
        BillDiscountResponse optimalDiscount = candidateOptimalDiscount.get(0);
        bill.setDiscountType(Enum.valueOf(DiscountType.class, optimalDiscount.getDiscountType()));
        bill.setDiscountAmount(optimalDiscount.getDiscountAmount());
        bill.setDiscountRate(optimalDiscount.getDiscountRate());
        bill.setDiscountDescription(optimalDiscount.getDiscountDescription());
        bill.setTotalBeforeDiscount(optimalDiscount.getTotalBeforeDiscount());
        bill.setTotalAfterDiscount(optimalDiscount.getTotalAfterDiscount());
        return bill;
    }
}
