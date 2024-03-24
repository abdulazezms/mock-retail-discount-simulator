package com.myretailbusiness.discountservice.controller;

import com.myretailbusiness.discountservice.constants.APIRoutes;
import com.myretailbusiness.discountservice.controller.body.bill.BillBody;
import com.myretailbusiness.discountservice.controller.docs.BillControllerDocs;
import com.myretailbusiness.discountservice.controller.response.bill.BillResponse;
import com.myretailbusiness.discountservice.service.bill.BillService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(APIRoutes.BILLINGS_CONTROLLER_REQUEST_MAPPING)
public class BillController {
    private final BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }

    @Operation(summary = BillControllerDocs.CREATE_BILL_SUMMARY,
            description = BillControllerDocs.CREATE_BILL_DESCRIPTION)
    @PostMapping
    public ResponseEntity<BillResponse> createBill(@RequestBody @Valid BillBody billBody) {
        return new ResponseEntity<>(billService.createBill(billBody), HttpStatus.CREATED);
    }
}
