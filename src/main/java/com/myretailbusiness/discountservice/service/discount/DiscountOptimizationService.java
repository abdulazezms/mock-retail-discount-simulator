package com.myretailbusiness.discountservice.service.discount;

import com.myretailbusiness.discountservice.domain.Bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountOptimizationService {
    private List<DiscountStrategy> discountStrategyList;

    @Autowired
    public DiscountOptimizationService(List<DiscountStrategy> discountStrategyList) {
        this.discountStrategyList = discountStrategyList;
    }

    public Bill applyOptimalDiscount(Bill bill) {
        return null;
    }
}
