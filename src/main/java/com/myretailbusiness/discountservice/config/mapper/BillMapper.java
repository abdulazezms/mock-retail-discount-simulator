package com.myretailbusiness.discountservice.config.mapper;

import com.myretailbusiness.discountservice.controller.body.bill.BillBody;
import com.myretailbusiness.discountservice.controller.response.bill.BillResponse;
import com.myretailbusiness.discountservice.domain.Bill;
import com.myretailbusiness.discountservice.domain.BillItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BillMapper {
    private final BaseResponseMapper baseResponseMapper;
    private final BillItemMapper billItemMapper;

    public BillMapper(BaseResponseMapper baseResponseMapper, BillItemMapper billItemMapper) {
        this.baseResponseMapper = baseResponseMapper;
        this.billItemMapper = billItemMapper;
    }

    public Bill toBill(BillBody body) {
        Bill bill = new Bill();
        List<BillItem> billItemList = body.getBillItemList().stream().map(billItemMapper::toBillItem).toList();
        bill.setBillItemList(billItemList);
        return bill;
    }

    public BillResponse toBillResponse(Bill body) {
        BillResponse billResponse = new BillResponse();
        baseResponseMapper.setBaseResponseAttributes(body, billResponse);
        billResponse.setId(body.getId());
        billResponse.setTotalBeforeDiscount(body.getTotalBeforeDiscount());
        billResponse.setTotalAfterDiscount(body.getTotalAfterDiscount());
        billResponse.setDiscountType(body.getDiscountType().name());
        billResponse.setDiscountDescription(body.getDiscountDescription());
        billResponse.setDiscountAmount(body.getDiscountAmount());
        billResponse.setDiscountRate(body.getDiscountRate());
        return billResponse;
    }
}
