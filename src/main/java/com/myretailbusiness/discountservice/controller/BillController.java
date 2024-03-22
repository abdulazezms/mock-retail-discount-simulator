package com.myretailbusiness.discountservice.controller;

import com.myretailbusiness.discountservice.constants.APIRoutes;
import com.myretailbusiness.discountservice.controller.bill.BillBody;
import com.myretailbusiness.discountservice.controller.response.bill.BillResponse;
import com.myretailbusiness.discountservice.service.bill.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(APIRoutes.BILLINGS_CONTROLLER_REQUEST_MAPPING)
public class BillController {
    private final BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }

    @PostMapping
    public ResponseEntity<BillResponse> createBill(@RequestBody BillBody billBody) {
        return new ResponseEntity<>(billService.createBill(billBody), HttpStatus.CREATED);
    }
}
