package com.superfintech.superpayment.service;

import com.superfintech.superpayment.entity.Payment;
import com.superfintech.superpayment.entity.status.EnumPaymentStatus;
import com.superfintech.superpayment.repository.PaymentRepository;
import com.superfintech.superpayment.service.interfaces.TransactionVerifier;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    TransactionVerifier transactionVerifier;

    @Transactional
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
        paymentRepository.save(payment);

        if (transactionVerifier.verifyTransaction(companyId, code, voucher)) {
            payment.setStatus(EnumPaymentStatus.ACCEPTED);
        } else {
            payment.setStatus(EnumPaymentStatus.REJECTED);
        }

        paymentRepository.save(payment);

        return payment.getStatus() == EnumPaymentStatus.ACCEPTED;
    }
}
