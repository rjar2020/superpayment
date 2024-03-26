package com.superfintech.superpayment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.superfintech.superpayment.controller.dto.ProcessPaymentRequest;
import com.superfintech.superpayment.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    public void processPaymentWhenAccepted() throws Exception {
        ProcessPaymentRequest request = new ProcessPaymentRequest();
        request.setCompanyId("testCompany");
        request.setCode("testCode");
        request.setVoucher("testVoucher");
        request.setOrderAmount(new BigDecimal("100.00"));

        when(paymentService.processPayment(any(String.class), any(String.class), any(String.class), any(BigDecimal.class)))
                .thenReturn(true);

        mockMvc.perform(post("/payment/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("Payment accepted"));
    }

    @Test
    @WithMockUser
    public void processPaymentWhenRejected() throws Exception {
        ProcessPaymentRequest request = new ProcessPaymentRequest();
        request.setCompanyId("testCompany");
        request.setCode("testCode");
        request.setVoucher("testVoucher");
        request.setOrderAmount(new BigDecimal("100.00"));

        when(paymentService.processPayment(any(String.class), any(String.class), any(String.class), any(BigDecimal.class)))
                .thenReturn(false);

        mockMvc.perform(post("/payment/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("Payment rejected"));
    }
}
