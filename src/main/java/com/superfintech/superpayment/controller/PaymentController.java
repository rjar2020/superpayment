package com.superfintech.superpayment.controller;

import com.superfintech.superpayment.controller.dto.ProcessPaymentRequest;
import com.superfintech.superpayment.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<String> process(@Valid @RequestBody ProcessPaymentRequest request) {
        boolean isPaymentAccepted = paymentService.processPayment(request.getCompanyId(),
                request.getCode(),
                request.getVoucher(),
                request.getOrderAmount());
        if (isPaymentAccepted) {
            return ResponseEntity.ok("Payment accepted");
        } else {
            return ResponseEntity.unprocessableEntity().body("Payment rejected");
        }
    }
}
