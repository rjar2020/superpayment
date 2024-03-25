package com.superfintech.superpayment.service;

import com.superfintech.superpayment.entity.Payment;
import com.superfintech.superpayment.entity.status.EnumPaymentStatus;
import com.superfintech.superpayment.repository.PaymentRepository;
import com.superfintech.superpayment.service.interfaces.TransactionVerifier;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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

    public List<Payment> getAllPayments() {
        return (List<Payment>) paymentRepository.findAll();
    }
}
