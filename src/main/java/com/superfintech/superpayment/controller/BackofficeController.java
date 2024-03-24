package com.superfintech.superpayment.controller;

import com.superfintech.superpayment.entity.Payment;
import com.superfintech.superpayment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/backoffice")
public class BackofficeController {

    @Autowired
    PaymentService paymentService;

    @RequestMapping("/payments")
    public String payment(Model model) {
        List<Payment> payments = paymentService.getAllPayments(); // Assuming this method returns a List<Payment>
        model.addAttribute("payments", payments);
        return "payments";
    }

}
