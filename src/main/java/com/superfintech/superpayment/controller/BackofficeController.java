package com.superfintech.superpayment.controller;

import com.superfintech.superpayment.entity.Payment;
import com.superfintech.superpayment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/backoffice")
public class BackofficeController {

    @Autowired
    private PaymentService paymentService;

    @RequestMapping("/payments")
    public String payments(Model model,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size) {
        Page<Payment> paymentPage = paymentService.getAllPayments(page, size);
        model.addAttribute("payments", paymentPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", paymentPage.getTotalPages());
        return "payments";
    }
}
