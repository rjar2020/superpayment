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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        String companyId = "company123";
        String code = "code123";
        String voucher = "voucher123";
        BigDecimal orderAmount = BigDecimal.valueOf(100);

        when(transactionVerifier.verifyTransaction(companyId, code, voucher)).thenReturn(true);

        boolean result = paymentService.processPayment(companyId, code, voucher, orderAmount);

        assertTrue(result);
        verify(paymentRepository, times(2)).save(any(Payment.class));
    }

    @Test
    public void testProcessPaymentWhenTransactionIsNotVerified() {
        String companyId = "company123";
        String code = "code123";
        String voucher = "voucher123";
        BigDecimal orderAmount = BigDecimal.valueOf(100);

        when(transactionVerifier.verifyTransaction(companyId, code, voucher)).thenReturn(false);

        boolean result = paymentService.processPayment(companyId, code, voucher, orderAmount);

        assertFalse(result);
        verify(paymentRepository, times(2)).save(any(Payment.class));
    }

    @Test
    public void testProcessPaymentWhenSaveFails() {
        String companyId = "failCompany";
        String code = "failCode";
        String voucher = "failVoucher";
        BigDecimal orderAmount = BigDecimal.valueOf(50);

        doThrow(DataIntegrityViolationException.class).when(paymentRepository).save(any(Payment.class));

        boolean result = paymentService.processPayment(companyId, code, voucher, orderAmount);

        assertFalse(result);
    }

    @Test
    public void testGetAllPaymentsPaginated() {
        Page<Payment> paymentPage = new PageImpl<>(List.of(new Payment(), new Payment()));
        when(paymentRepository.findAll(PageRequest.of(0, 10))).thenReturn(paymentPage);

        Page<Payment> result = paymentService.getAllPayments(0, 10);

        assertNotNull(result);
        assertEquals(2, result.getContent().size(), "The payment list should contain two items");
        verify(paymentRepository, times(1)).findAll(PageRequest.of(0, 10));
    }

    @Test
    public void testGetAllPaymentsWhenEmpty() {
        Page<Payment> emptyPage = new PageImpl<>(List.of());
        when(paymentRepository.findAll(PageRequest.of(0, 10))).thenReturn(emptyPage);

        Page<Payment> result = paymentService.getAllPayments(0, 10);

        assertTrue(result.getContent().isEmpty(), "The payment list should be empty");
    }

    @Test
    public void testGetAllPaymentsWithLargePageSize() {
        List<Payment> largeList = new ArrayList<>(Collections.nCopies(1000, new Payment()));
        Page<Payment> largePage = new PageImpl<>(largeList, PageRequest.of(0, 1000), largeList.size());
        when(paymentRepository.findAll(PageRequest.of(0, 1000))).thenReturn(largePage);

        Page<Payment> result = paymentService.getAllPayments(0, 1000);

        assertEquals(1000, result.getContent().size(), "The payment list should match the large page size");
    }

    @Test
    public void testGetAllPaymentsWithNegativePageNumber() {
        // Setup
        int invalidPageNumber = -1;
        int pageSize = 10;
        Page<Payment> expectedPage = Page.empty();

        // Mocking the repository to return an empty page for any page request
        when(paymentRepository.findAll(any(Pageable.class))).thenReturn(expectedPage);

        // Execution
        Page<Payment> result = paymentService.getAllPayments(invalidPageNumber, pageSize);

        // Verification
        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        verify(paymentRepository).findAll((Pageable) argThat(pageRequest -> {
            PageRequest request = (PageRequest) pageRequest;
            return request.getPageNumber() == 0 && request.getPageSize() == pageSize;
        }));
    }
}
