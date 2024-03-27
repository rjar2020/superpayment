package com.superfintech.superpayment.service;

import com.superfintech.superpayment.entity.Payment;
import com.superfintech.superpayment.entity.status.EnumPaymentStatus;
import com.superfintech.superpayment.repository.PaymentRepository;
import com.superfintech.superpayment.service.interfaces.TransactionVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    @Qualifier("voucherTransactionVerifier")
    TransactionVerifier transactionVerifier;


    public boolean processPayment(String companyId,
                                  String code,
                                  String voucher,
                                  BigDecimal orderAmount) {

        Payment payment = Payment.builder()
                .companyId(companyId)
                .code(code)
                .voucher(voucher)
                .orderAmount(orderAmount)
                .status(EnumPaymentStatus.PENDING)
                .build();

        if (!savePayment(payment)) return false;

        payment.setStatus(transactionVerifier.verifyTransaction(companyId, code, voucher) ?
                EnumPaymentStatus.ACCEPTED : EnumPaymentStatus.REJECTED);

        if (!savePayment(payment)) return false;

        return payment.getStatus() == EnumPaymentStatus.ACCEPTED;
    }

    private boolean savePayment(Payment payment) {
        try {
            paymentRepository.save(payment);
        } catch (DataIntegrityViolationException e) {
            return false;
        }
        return true;
    }

    public Page<Payment> getAllPayments(int pageNumber, int pageSize) {
        // Ensure non-negative page numbers and a minimum page size of 1
        int safePageNumber = Math.max(pageNumber, 0);
        int safePageSize = Math.max(pageSize, 1);

        return paymentRepository.findAll(PageRequest.of(safePageNumber, safePageSize));
    }
}
