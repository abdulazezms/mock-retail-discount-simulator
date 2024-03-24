package com.myretailbusiness.discountservice.config.mapper;

import com.myretailbusiness.discountservice.controller.body.bill.BillBody;
import com.myretailbusiness.discountservice.controller.response.bill.BillResponse;
import com.myretailbusiness.discountservice.domain.Bill;
import com.myretailbusiness.discountservice.domain.BillItem;
import com.myretailbusiness.discountservice.domain.DiscountType;
import com.myretailbusiness.discountservice.utils.JwtUtils;
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
        bill.setUserEmail(JwtUtils.getUserEmailClaim());
        bill.setTotalBeforeDiscount(getBillTotal(bill));
        return bill;
    }

    private Double getBillTotal(Bill bill) {
        return bill.getBillItemList()
                .stream()
                .map(billItem -> billItem.getProduct().getPrice() * (double) billItem.getQuantity())
                .reduce(0.0, Double::sum);
    }

    public BillResponse toBillDiscountResponse(Bill body) {
        BillResponse billResponse = BillResponse
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
