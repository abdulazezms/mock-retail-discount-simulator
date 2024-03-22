package com.myretailbusiness.discountservice.controller.body.bill;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class BillItemBody {
    @NotEmpty(message = "productId mustn't be empty")
    private String productId;
    @Min(value = 1, message = "quantity must be at least 1")
    @Max(value = 100, message = "quantity cannot exceed 100")
    @NotNull(message= "quantity must not be empty")
    private Integer quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillItemBody that = (BillItemBody) o;
        return Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
