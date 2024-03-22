package com.myretailbusiness.discountservice.controller.bill;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BillBody {
     private List<BillItemBody> billItemList;
}
