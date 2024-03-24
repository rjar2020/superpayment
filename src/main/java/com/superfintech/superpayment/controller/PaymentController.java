package com.superfintech.superpayment.controller;

import com.superfintech.superpayment.controller.dto.ProcessPaymentRequest;
import com.superfintech.superpayment.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @Value("${server.url}")
    String serverUrl;

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

    @GetMapping("/paymentForm")
    public String paymentForm(
            @RequestParam(name = "companyId", defaultValue = "") String companyId,
            @RequestParam(name = "orderAmount", defaultValue = "") String orderAmount,
            Model model) {
        model.addAttribute("companyId", companyId);
        model.addAttribute("orderAmount", orderAmount);
        model.addAttribute("serverUrl", serverUrl + "/payment/process" );
        return "paymentForm";
    }
}
