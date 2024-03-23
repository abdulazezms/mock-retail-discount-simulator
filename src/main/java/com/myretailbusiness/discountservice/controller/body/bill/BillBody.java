package com.myretailbusiness.discountservice.controller.body.bill;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class BillBody {
     @NotEmpty(message = "bill must contain at least one item")
     @UniqueElements(message = "ProductId must be unique")
     @Valid
     private List<BillItemBody> billItemList;

     @Override
     public boolean equals(Object o) {
          if (this == o) return true;
          if (o == null || getClass() != o.getClass()) return false;
          BillBody billBody = (BillBody) o;
          return Objects.equals(billItemList, billBody.billItemList);
     }

     @Override
     public int hashCode() {
          return Objects.hash(billItemList);
     }
}
