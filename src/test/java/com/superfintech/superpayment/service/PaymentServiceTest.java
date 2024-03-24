package com.superfintech.superpayment.service;

import com.superfintech.superpayment.entity.Payment;
import com.superfintech.superpayment.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private VoucherTransactionVerifier transactionVerifier;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcessPaymentWhenTransactionIsVerified() {
        // Given
        String companyId = "company123";
        String code = "code123";
        String voucher = "voucher123";
        BigDecimal orderAmount = BigDecimal.valueOf(100);

        when(transactionVerifier.verifyTransaction(companyId, code, voucher)).thenReturn(true);

        // When
        paymentService.processPayment(companyId, code, voucher, orderAmount);

        // Then
        verify(paymentRepository, times(2)).save(any(Payment.class));
    }

    @Test
    public void testProcessPaymentWhenTransactionIsNotVerified() {
        // Given
        String companyId = "company123";
        String code = "code123";
        String voucher = "voucher123";
        BigDecimal orderAmount = BigDecimal.valueOf(100);

        when(transactionVerifier.verifyTransaction(companyId, code, voucher)).thenReturn(false);

        // When
        paymentService.processPayment(companyId, code, voucher, orderAmount);

        // Then
        verify(paymentRepository, times(2)).save(any(Payment.class));
    }
}
