package com.superfintech.superpayment.controller;

import com.superfintech.superpayment.entity.Payment;
import com.superfintech.superpayment.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(BackofficeController.class)
public class BackofficeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private PaymentService paymentService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser // Simulate an authenticated user
    public void payment_ShouldAddPaymentEntriesToModel_AndReturnPaymentsView() throws Exception {
        // Arrange
        Payment mockPayment1 = new Payment();
        Payment mockPayment2 = new Payment();
        when(paymentService.getAllPayments()).thenReturn(Arrays.asList(mockPayment1, mockPayment2));

        // Act & Assert
        mockMvc.perform(get("/backoffice/payments"))
                .andExpect(status().isOk())
                .andExpect(view().name("payments"))
                .andExpect(model().attribute("payments", hasSize(2)));
    }
}
