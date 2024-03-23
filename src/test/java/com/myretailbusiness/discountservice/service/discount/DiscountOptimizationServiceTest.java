package com.myretailbusiness.discountservice.service.discount;

import com.myretailbusiness.discountservice.controller.body.auth.LoginBody;
import com.myretailbusiness.discountservice.controller.response.auth.LoginResponse;
import com.myretailbusiness.discountservice.controller.response.bill.BillDiscountResponse;
import com.myretailbusiness.discountservice.domain.Bill;
import com.myretailbusiness.discountservice.domain.DiscountType;
import com.myretailbusiness.discountservice.exception.IncorrectCredentialsException;
import com.myretailbusiness.discountservice.exception.NoResourceFoundException;
import com.myretailbusiness.discountservice.exception.ServiceUnavailableException;
import com.myretailbusiness.discountservice.httpclient.client.keycloak.KeycloakClient;
import com.myretailbusiness.discountservice.httpclient.response.keycloak.KeycloakAuthenticationResponse;
import com.myretailbusiness.discountservice.service.keycloak.KeycloakServiceImpl;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static com.myretailbusiness.discountservice.service.discount.DiscountOptimizationServiceTestUtil.*;
import static com.myretailbusiness.discountservice.service.keycloak.KeycloakServiceTestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        BillDiscountResponse appliedPercentageDiscount = applyPercentageDiscountWithRate(billBeforeDiscount, 0.3);
        BillDiscountResponse appliedVolumeDiscount = applyVolumeDiscountWithRate(billBeforeDiscount, 0.1);

        when(discountStrategy1.getOptimalDiscountInfo(billBeforeDiscount)).thenReturn(appliedPercentageDiscount);
        when(discountStrategy2.getOptimalDiscountInfo(billBeforeDiscount)).thenReturn(appliedVolumeDiscount);


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

        verify(discountStrategy1, times(1)).getOptimalDiscountInfo(billBeforeDiscount);
        verify(discountStrategy2, times(1)).getOptimalDiscountInfo(billBeforeDiscount);
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

        verify(discountStrategy1, never()).getOptimalDiscountInfo(any());
        verify(discountStrategy2, never()).getOptimalDiscountInfo(any());
    }
}
