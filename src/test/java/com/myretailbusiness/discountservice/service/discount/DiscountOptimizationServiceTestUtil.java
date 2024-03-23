package com.myretailbusiness.discountservice.service.discount;

import com.myretailbusiness.discountservice.controller.body.auth.LoginBody;
import com.myretailbusiness.discountservice.controller.response.bill.BillDiscountResponse;
import com.myretailbusiness.discountservice.domain.*;
import com.myretailbusiness.discountservice.httpclient.response.keycloak.KeycloakAuthenticationResponse;

import java.util.List;
import java.util.Map;

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

    public static BillDiscountResponse applyPercentageDiscountWithRate(Bill bill, double rate) {
        Double totalBeforeDiscount = bill.getTotalBeforeDiscount();
        BillDiscountResponse billDiscountResponse = new BillDiscountResponse();
        billDiscountResponse.setDiscountType(DiscountType.PERCENTAGE_DISCOUNT.name());
        billDiscountResponse.setDiscountRate(rate);
        billDiscountResponse.setDiscountDescription("P description");
        billDiscountResponse.setDiscountAmount(rate * totalBeforeDiscount);
        billDiscountResponse.setTotalBeforeDiscount(totalBeforeDiscount);
        billDiscountResponse.setTotalAfterDiscount(
                totalBeforeDiscount - billDiscountResponse.getDiscountAmount()
        );
        return billDiscountResponse;
    }

    public static BillDiscountResponse applyVolumeDiscountWithRate(Bill bill, double rate) {
        Double totalBeforeDiscount = bill.getTotalBeforeDiscount();
        BillDiscountResponse billDiscountResponse = new BillDiscountResponse();
        billDiscountResponse.setDiscountType(DiscountType.VOLUME_DISCOUNT.name());
        billDiscountResponse.setDiscountRate(rate);
        billDiscountResponse.setDiscountDescription("V description");
        billDiscountResponse.setDiscountAmount(rate * totalBeforeDiscount);
        billDiscountResponse.setTotalBeforeDiscount(totalBeforeDiscount);
        billDiscountResponse.setTotalAfterDiscount(
                totalBeforeDiscount - billDiscountResponse.getDiscountAmount()
        );
        return billDiscountResponse;
    }
}
