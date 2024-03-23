package com.myretailbusiness.discountservice.service.discount;

import com.myretailbusiness.discountservice.controller.response.bill.BillResponse;
import com.myretailbusiness.discountservice.domain.*;

import java.util.List;

public class DiscountOptimizationServiceTestUtil {

    public static Bill getBillBeforeDiscount() {
        Bill bill = new Bill();
        bill.setBillItemList(getBillItemList());
        bill.setTotalBeforeDiscount(100.0);
        bill.setUserEmail("email@exampl.com");
        return bill;
    }

    public static List<BillItem> getBillItemList() {
        BillItem billItem1 = new BillItem();
        billItem1.setProduct(getProductWithPrice(11.0));

        BillItem billItem2 = new BillItem();
        billItem2.setProduct(getProductWithPrice(100.0));
        return List.of(billItem1, billItem2);
    }

    public static Product getProductWithPrice(double price) {
        Product product = new Product();
        product.setName("product");
        product.setPrice(price);
        product.setCategory(getCategory());
        return product;
    }

    public static Category getCategory() {
        Category category = new Category("GROCERY");
        return category;
    }

    public static BillResponse applyPercentageDiscountWithRate(Bill bill, double rate) {
        Double totalBeforeDiscount = bill.getTotalBeforeDiscount();
        BillResponse billResponse = new BillResponse();
        billResponse.setDiscountType(DiscountType.PERCENTAGE_DISCOUNT.name());
        billResponse.setDiscountRate(rate);
        billResponse.setDiscountDescription("P description");
        billResponse.setDiscountAmount(rate * totalBeforeDiscount);
        billResponse.setTotalBeforeDiscount(totalBeforeDiscount);
        billResponse.setTotalAfterDiscount(
                totalBeforeDiscount - billResponse.getDiscountAmount()
        );
        return billResponse;
    }

    public static BillResponse applyVolumeDiscountWithRate(Bill bill, double rate) {
        Double totalBeforeDiscount = bill.getTotalBeforeDiscount();
        BillResponse billResponse = new BillResponse();
        billResponse.setDiscountType(DiscountType.VOLUME_DISCOUNT.name());
        billResponse.setDiscountRate(rate);
        billResponse.setDiscountDescription("V description");
        billResponse.setDiscountAmount(rate * totalBeforeDiscount);
        billResponse.setTotalBeforeDiscount(totalBeforeDiscount);
        billResponse.setTotalAfterDiscount(
                totalBeforeDiscount - billResponse.getDiscountAmount()
        );
        return billResponse;
    }
}
