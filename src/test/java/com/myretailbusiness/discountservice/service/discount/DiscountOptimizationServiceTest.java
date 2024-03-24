package com.myretailbusiness.discountservice.service.discount;

import com.myretailbusiness.discountservice.controller.response.bill.BillResponse;
import com.myretailbusiness.discountservice.domain.Bill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.myretailbusiness.discountservice.service.discount.DiscountOptimizationServiceTestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DiscountOptimizationServiceTest {

    @Mock
    private DiscountStrategy discountStrategy1;

    @Mock
    private DiscountStrategy discountStrategy2;

    @InjectMocks
    private DiscountOptimizationService discountOptimizationService;

    private List<DiscountStrategy> discountStrategyList;

    @BeforeEach
    void setUp() {
        discountStrategyList = Arrays.asList(discountStrategy1, discountStrategy2);
        discountOptimizationService = new DiscountOptimizationService(discountStrategyList);
    }

    @Test public void Should_ReturnOptimalPercentageDiscount_When_OptimalPercentageDiscountIsBetterThanOptimalVolumeDiscount() {
        Bill billBeforeDiscount = getBillBeforeDiscount();

        // Assuming percentage discount provides a better discount than volume discount
        BillResponse appliedPercentageDiscount = applyPercentageDiscountWithRate(billBeforeDiscount, 0.3);
        BillResponse appliedVolumeDiscount = applyVolumeDiscountWithRate(billBeforeDiscount, 0.1);

        when(discountStrategy1.getBillAfterOptimalDiscount(billBeforeDiscount)).thenReturn(appliedPercentageDiscount);
        when(discountStrategy2.getBillAfterOptimalDiscount(billBeforeDiscount)).thenReturn(appliedVolumeDiscount);


        Bill actualBillAfterDiscount = discountOptimizationService.applyOptimalDiscount(billBeforeDiscount);
        assertEquals(appliedPercentageDiscount.getTotalBeforeDiscount(),
                actualBillAfterDiscount.getTotalBeforeDiscount());

        assertEquals(appliedPercentageDiscount.getTotalAfterDiscount(),
                actualBillAfterDiscount.getTotalAfterDiscount());

        assertEquals(appliedPercentageDiscount.getDiscountType(),
                actualBillAfterDiscount.getDiscountType().name());

        assertEquals(appliedPercentageDiscount.getDiscountDescription(),
                actualBillAfterDiscount.getDiscountDescription());

        assertEquals(appliedPercentageDiscount.getDiscountRate(),
                actualBillAfterDiscount.getDiscountRate());

        assertEquals(appliedPercentageDiscount.getDiscountAmount(),
                actualBillAfterDiscount.getDiscountAmount());

        verify(discountStrategy1, times(1)).getBillAfterOptimalDiscount(billBeforeDiscount);
        verify(discountStrategy2, times(1)).getBillAfterOptimalDiscount(billBeforeDiscount);
    }

    @Test public void Should_ReturnOriginalBill_When_DiscountStrategiesThere() {
        discountOptimizationService = new DiscountOptimizationService(Collections.emptyList());
        Bill billBeforeDiscount = getBillBeforeDiscount();

        Bill actualBillAfterDiscount = discountOptimizationService.applyOptimalDiscount(billBeforeDiscount);


        assertEquals(billBeforeDiscount.getTotalBeforeDiscount(),
                actualBillAfterDiscount.getTotalBeforeDiscount());

        assertEquals(billBeforeDiscount.getTotalAfterDiscount(),
                actualBillAfterDiscount.getTotalAfterDiscount());

        assertEquals(billBeforeDiscount.getDiscountType(),
                actualBillAfterDiscount.getDiscountType());

        assertEquals(billBeforeDiscount.getDiscountDescription(),
                actualBillAfterDiscount.getDiscountDescription());

        assertEquals(billBeforeDiscount.getDiscountRate(),
                actualBillAfterDiscount.getDiscountRate());

        assertEquals(billBeforeDiscount.getDiscountAmount(),
                actualBillAfterDiscount.getDiscountAmount());

        verify(discountStrategy1, never()).getBillAfterOptimalDiscount(any());
        verify(discountStrategy2, never()).getBillAfterOptimalDiscount(any());
    }
}
