package com.myretailbusiness.discountservice.config.mapper;

import com.myretailbusiness.discountservice.controller.body.bill.BillItemBody;
import com.myretailbusiness.discountservice.domain.BillItem;
import com.myretailbusiness.discountservice.service.product.ProductService;
import org.springframework.stereotype.Component;

@Component
public class BillItemMapper {

    private ProductService productService;

    public BillItemMapper(ProductService productService) {
        this.productService = productService;
    }

    public BillItem toBillItem(BillItemBody billItemBody) {
        BillItem billItem = new BillItem();
        billItem.setQuantity(billItemBody.getQuantity());
        billItem.setProduct(productService.findProductById(billItemBody.getProductId()));
        return billItem;
    }
}
