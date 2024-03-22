package com.myretailbusiness.discountservice.config.mapper;

import com.myretailbusiness.discountservice.controller.body.bill.BillBody;
import com.myretailbusiness.discountservice.controller.response.bill.BillDiscountResponse;
import com.myretailbusiness.discountservice.domain.Bill;
import com.myretailbusiness.discountservice.domain.BillItem;
import com.myretailbusiness.discountservice.domain.DiscountType;
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

    public BillDiscountResponse toBillDiscountResponse(Bill body) {
        BillDiscountResponse billResponse = BillDiscountResponse
                .builder()
                .totalBeforeDiscount(body.getTotalBeforeDiscount())
                .totalAfterDiscount(body.getTotalAfterDiscount())
                .discountType(body.getDiscountType() == null? DiscountType.NONE.name() : body.getDiscountType().name())
                .discountDescription(body.getDiscountDescription())
                .discountAmount(body.getDiscountAmount())
                .discountRate(body.getDiscountRate())
                .build();
        baseResponseMapper.setBaseResponseAttributes(body, billResponse);
        return billResponse;
    }
}
