package com.myretailbusiness.discountservice.controller.bill;

import com.myretailbusiness.discountservice.controller.body.bill.BillBody;
import com.myretailbusiness.discountservice.controller.body.bill.BillItemBody;
import com.myretailbusiness.discountservice.controller.response.bill.BillResponse;

import java.time.LocalDateTime;
import java.util.List;

public class BillControllerTestUtil {

    public static BillBody getValidJsonBodyForBillCreation(){
        BillBody billBody = new BillBody();
        BillItemBody billItemBody = new BillItemBody();
        billItemBody.setProductId("P12345");
        billItemBody.setQuantity(10);
        billBody.setBillItemList(List.of(billItemBody));
        return billBody;
    }

    public static BillResponse getDiscountResponse() {
        BillResponse billResponse = new BillResponse();
        billResponse.setDiscountDescription("Some description");
        billResponse.setDiscountAmount(12.0);
        billResponse.setDiscountType("NONE");
        billResponse.setTotalAfterDiscount(22.0);
        billResponse.setTotalBeforeDiscount(34.0);
        billResponse.setId("id");
        billResponse.setDiscountRate(0.2);
        billResponse.setCreatedByUser("test");
        billResponse.setCreatedDate(LocalDateTime.MAX);
        billResponse.setModifiedByUser("test");
        billResponse.setCreatedDate(LocalDateTime.MAX);
        return billResponse;
    }

    public static BillBody getJsonBodyForBillCreationWithLargeItemQuantity(){
        BillBody billBody = new BillBody();
        BillItemBody billItemBody = new BillItemBody();
        billItemBody.setProductId("P12345");
        billItemBody.setQuantity(10000);
        billBody.setBillItemList(List.of(billItemBody));
        return billBody;
    }

    public static BillBody getJsonBodyForBillCreationWithNonUniqueProductIds(){
        BillBody billBody = new BillBody();
        BillItemBody billItemBody1 = new BillItemBody();
        billItemBody1.setProductId("P12345");
        billItemBody1.setQuantity(1);

        BillItemBody billItemBody2 = new BillItemBody();
        billItemBody2.setProductId("P12345");
        billItemBody2.setQuantity(1);

        billBody.setBillItemList(List.of(billItemBody1, billItemBody2));
        return billBody;
    }

    public static BillBody getEmptyJsonBodyForBill(){
        return new BillBody();
    }
}
