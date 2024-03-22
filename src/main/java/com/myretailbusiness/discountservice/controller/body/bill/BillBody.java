package com.myretailbusiness.discountservice.controller.body.bill;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

@Getter
@Setter
public class BillBody {
     @NotEmpty(message = "bill must contain at least one item")
     @UniqueElements(message = "ProductId must be unique")
     @Valid
     private List<BillItemBody> billItemList;
}
