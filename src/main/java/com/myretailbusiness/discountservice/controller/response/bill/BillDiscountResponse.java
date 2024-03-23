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
public class BillDiscountResponse extends BaseEntityResponse implements Comparable<BillDiscountResponse> {
    private Double totalBeforeDiscount;
    private Double totalAfterDiscount;
    private String discountType;
    private String discountDescription;
    private Double discountRate;
    private Double discountAmount;
    @Override
    public int compareTo(BillDiscountResponse other) {
        return Double.compare(other.discountAmount, this.discountAmount); // Descending order by discount amount
    }
    @Override
    public String toString() {
        return "BillDiscountResponse{" +
                "discountType='" + discountType + '\'' +
                ", discountDescription='" + discountDescription + '\'' +
                ", discountRate=" + discountRate +
                ", discountAmount=" + discountAmount +
                '}';
    }

    public static BillDiscountResponse getNoDiscountFromBill(Bill bill) {
        return BillDiscountResponse
                .builder()
                .totalAfterDiscount(bill.getTotalBeforeDiscount())
                .totalBeforeDiscount(bill.getTotalBeforeDiscount())
                .discountRate(0.0)
                .discountAmount(0.0)
                .discountType(DiscountType.NONE.name())
                .build();
    }
}
