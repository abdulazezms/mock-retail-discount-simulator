package com.myretailbusiness.discountservice.controller.response.bill;

import com.myretailbusiness.discountservice.controller.response.BaseEntityResponse;
import com.myretailbusiness.discountservice.domain.Bill;
import com.myretailbusiness.discountservice.domain.DiscountType;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillResponse extends BaseEntityResponse implements Comparable<BillResponse> {
    private Double totalBeforeDiscount;
    private Double totalAfterDiscount;
    private String discountType;
    private String discountDescription;
    private Double discountRate;
    private Double discountAmount;
    @Override
    public int compareTo(BillResponse other) {
        return Double.compare(other.discountAmount, this.discountAmount); // Descending order by discount amount
    }
    @Override
    public String toString() {
        return "BillResponse{" +
                "discountType='" + discountType + '\'' +
                ", discountDescription='" + discountDescription + '\'' +
                ", discountRate=" + discountRate +
                ", discountAmount=" + discountAmount +
                '}';
    }

    public static BillResponse getNoDiscountFromBill(Bill bill) {
        return BillResponse
                .builder()
                .totalAfterDiscount(bill.getTotalBeforeDiscount())
                .totalBeforeDiscount(bill.getTotalBeforeDiscount())
                .discountRate(0.0)
                .discountAmount(0.0)
                .discountType(DiscountType.NONE.name())
                .build();
    }
}
