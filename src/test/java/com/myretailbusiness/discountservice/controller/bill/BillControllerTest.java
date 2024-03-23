package com.myretailbusiness.discountservice.controller.bill;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretailbusiness.discountservice.constants.APIRoutes;
import com.myretailbusiness.discountservice.constants.AppProfiles;
import com.myretailbusiness.discountservice.controller.BillController;
import com.myretailbusiness.discountservice.service.bill.BillService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.myretailbusiness.discountservice.controller.bill.BillControllerTestUtil.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = BillController.class)
@ActiveProfiles(AppProfiles.UNIT_TEST_PROFILE)
@AutoConfigureMockMvc(addFilters = false)
public class BillControllerTest {
    @Autowired private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;

    @MockBean private BillService billService;

    @Test public void Should_ReturnCreatedStatusCode_When_BillBodyValid() throws Exception {
        when(billService.createBill(getValidJsonBodyForBillCreation())).thenReturn(getDiscountResponse());
        mockMvc
                .perform(
                        post(APIRoutes.BILLINGS_CONTROLLER_REQUEST_MAPPING)
                        .content(objectMapper.writeValueAsString(getValidJsonBodyForBillCreation()))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(getDiscountResponse())));
        verify(billService, times(1)).createBill(getValidJsonBodyForBillCreation());
    }

    @Test public void Should_ReturnBadRequestStatusCode_When_BillBodyItemHasLargeQuantity() throws Exception {
        mockMvc
                .perform(
                        post(APIRoutes.BILLINGS_CONTROLLER_REQUEST_MAPPING)
                                .content(objectMapper.writeValueAsString(getJsonBodyForBillCreationWithLargeItemQuantity()))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
        verify(billService, never()).createBill(any());
    }

    @Test public void Should_ReturnBadRequestStatusCode_When_BillBodyHasNonUniqueProductIds() throws Exception {
        mockMvc
                .perform(
                        post(APIRoutes.BILLINGS_CONTROLLER_REQUEST_MAPPING)
                                .content(objectMapper.writeValueAsString(getJsonBodyForBillCreationWithNonUniqueProductIds()))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
        verify(billService, never()).createBill(any());
    }

    @Test public void Should_ReturnBadRequestStatusCode_When_BillBodyIsEmpty() throws Exception {
        mockMvc
                .perform(
                        post(APIRoutes.BILLINGS_CONTROLLER_REQUEST_MAPPING)
                                .content(objectMapper.writeValueAsString(getEmptyJsonBodyForBill()))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
        verify(billService, never()).createBill(any());
    }
}
