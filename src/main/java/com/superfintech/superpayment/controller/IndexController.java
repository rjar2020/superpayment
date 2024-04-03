package com.superfintech.superpayment.controller;

import com.superfintech.superpayment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    PaymentService paymentService;

    @Value("${server.url}")
    String serverUrl;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("serverUrl", serverUrl );
        return "index";
    }
}
