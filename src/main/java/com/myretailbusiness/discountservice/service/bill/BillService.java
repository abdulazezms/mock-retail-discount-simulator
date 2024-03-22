package com.myretailbusiness.discountservice.service.bill;

import com.myretailbusiness.discountservice.config.mapper.BillMapper;
import com.myretailbusiness.discountservice.controller.body.bill.BillBody;
import com.myretailbusiness.discountservice.controller.response.bill.BillResponse;
import com.myretailbusiness.discountservice.domain.Bill;
import com.myretailbusiness.discountservice.repository.BillRepository;
import com.myretailbusiness.discountservice.service.discount.DiscountOptimizationService;
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


    public BillResponse createBill(BillBody billBody) {
        Bill bill = billMapper.toBill(billBody);
        discountOptimizationService.applyOptimalDiscount(bill);
        return billMapper.toBillResponse(repository.save(bill));
    }
}
