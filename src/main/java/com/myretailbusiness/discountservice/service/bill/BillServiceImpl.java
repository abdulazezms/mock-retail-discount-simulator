package com.myretailbusiness.discountservice.service.bill;

import com.myretailbusiness.discountservice.config.mapper.BillMapper;
import com.myretailbusiness.discountservice.controller.bill.BillBody;
import com.myretailbusiness.discountservice.controller.bill.BillItemBody;
import com.myretailbusiness.discountservice.controller.response.bill.BillResponse;
import com.myretailbusiness.discountservice.domain.Bill;
import com.myretailbusiness.discountservice.domain.DiscountType;
import com.myretailbusiness.discountservice.domain.Product;
import com.myretailbusiness.discountservice.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BillServiceImpl implements BillService {
    private final BillRepository repository;

    private final BillMapper billMapper;

    private final ProductService productService;
    @Autowired
    public BillServiceImpl(BillRepository repository,
                           BillMapper billMapper,
                           ProductService productService) {
        this.repository = repository;
        this.billMapper = billMapper;
        this.productService = productService;
    }


    @Override
    public BillResponse createBill(BillBody billBody) {
        Bill entity = billMapper.toBill(billBody);
        entity.setDiscountType(DiscountType.NONE);
        //TODO: Call discount service to fetch the best discount.
        return billMapper.toBillResponse(repository.save(entity));
    }
}
