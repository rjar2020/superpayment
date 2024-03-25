package com.superfintech.superpayment.service;

import com.superfintech.superpayment.entity.Payment;
import com.superfintech.superpayment.repository.PaymentRepository;
import com.superfintech.superpayment.service.interfaces.TransactionVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private TransactionVerifier transactionVerifier;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcessPaymentWhenTransactionIsVerified() {
        // Setup
        String companyId = "company123";
        String code = "code123";
        String voucher = "voucher123";
        BigDecimal orderAmount = BigDecimal.valueOf(100);

        // Mocking
        when(transactionVerifier.verifyTransaction(companyId, code, voucher)).thenReturn(true);

        // Execution
        boolean result = paymentService.processPayment(companyId, code, voucher, orderAmount);

        // Verification
        assertTrue(result);
        verify(paymentRepository, times(2)).save(any(Payment.class));
    }

    @Test
    public void testProcessPaymentWhenTransactionIsNotVerified() {
        // Setup
        String companyId = "company123";
        String code = "code123";
        String voucher = "voucher123";
        BigDecimal orderAmount = BigDecimal.valueOf(100);

        // Mocking
        when(transactionVerifier.verifyTransaction(companyId, code, voucher)).thenReturn(false);

        // Execution
        boolean result = paymentService.processPayment(companyId, code, voucher, orderAmount);

        // Verification
        assertFalse(result);
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    public void testProcessPaymentWhenSaveFails() {
        // Setup
        String companyId = "failCompany";
        String code = "failCode";
        String voucher = "failVoucher";
        BigDecimal orderAmount = BigDecimal.valueOf(50);

        // Mocking
        doThrow(DataIntegrityViolationException.class).when(paymentRepository).save(any(Payment.class));

        // Execution
        boolean result = paymentService.processPayment(companyId, code, voucher, orderAmount);

        // Verification
        assertFalse(result);
    }

    @Test
    public void testGetAllPayments() {
        // Mocking
        Payment payment1 = new Payment();
        Payment payment2 = new Payment();
        when(paymentRepository.findAll()).thenReturn(List.of(payment1, payment2));

        // Execution
        List<Payment> payments = paymentService.getAllPayments();

        // Verification
        assertNotNull(payments);
        assertEquals(2, payments.size());
        verify(paymentRepository, times(1)).findAll();
    }
}
