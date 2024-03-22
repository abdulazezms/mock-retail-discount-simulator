package com.myretailbusiness.discountservice.service.bill;

import com.myretailbusiness.discountservice.config.mapper.BillMapper;
import com.myretailbusiness.discountservice.controller.body.bill.BillBody;
import com.myretailbusiness.discountservice.controller.response.bill.BillDiscountResponse;
import com.myretailbusiness.discountservice.domain.Bill;
import com.myretailbusiness.discountservice.repository.BillRepository;
import com.myretailbusiness.discountservice.service.discount.DiscountOptimizationService;
import com.myretailbusiness.discountservice.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillService {
    private final BillRepository repository;

    private final BillMapper billMapper;

    private final DiscountOptimizationService discountOptimizationService;
    @Autowired
    public BillService(BillRepository repository,
                           BillMapper billMapper,
                           DiscountOptimizationService discountOptimizationService) {
        this.repository = repository;
        this.billMapper = billMapper;
        this.discountOptimizationService = discountOptimizationService;
    }


    public BillDiscountResponse createBill(BillBody billBody) {
        Bill bill = billMapper.toBill(billBody);
        bill.setTotalBeforeDiscount(getBillTotal(bill));
        bill.setUserEmail(JwtUtils.getUserEmailClaim());
        discountOptimizationService.applyOptimalDiscount(bill);
        return billMapper.toBillDiscountResponse(repository.save(bill));
    }
    private Double getBillTotal(Bill bill) {
        return bill.getBillItemList()
                .stream()
                .map(billItem -> billItem.getProduct().getPrice() * (double) billItem.getQuantity())
                .reduce(0.0, Double::sum);
    }
}
