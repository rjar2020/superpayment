package com.superfintech.superpayment.integration;

import com.superfintech.superpayment.config.persistence.JpaAuditingConfiguration;
import com.superfintech.superpayment.entity.Payment;
import com.superfintech.superpayment.entity.status.EnumPaymentStatus;
import com.superfintech.superpayment.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(JpaAuditingConfiguration.class)
public class PaymentAuditingIntegrationTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    public void whenSavePayment_thenAuditingFieldsPopulated() {
        Payment payment = Payment.builder()
                .companyId("123")
                .code("code123")
                .voucher("voucher123")
                .orderAmount(BigDecimal.valueOf(100))
                .status(EnumPaymentStatus.PENDING)
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        assertNotNull(savedPayment.getInsertTimestamp(), "Insert timestamp should be populated");
        assertNotNull(savedPayment.getLastUpdateTimestamp(), "Last update timestamp should be populated");

        assertTrue(savedPayment.getInsertTimestamp().isBefore(LocalDateTime.now().plusMinutes(1)), "Insert timestamp should be recent");
        assertTrue(savedPayment.getLastUpdateTimestamp().isBefore(LocalDateTime.now().plusMinutes(1)), "Last update timestamp should be recent");
    }
}

