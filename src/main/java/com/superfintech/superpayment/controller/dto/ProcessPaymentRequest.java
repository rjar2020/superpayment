package com.superfintech.superpayment.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProcessPaymentRequest {
    @NotNull(message = "companyId is required")
    private String companyId;
    @NotNull(message = "code is required")
    private String code;
    @NotNull(message = "voucher is required")
    private String voucher;
    @NotNull(message = "orderAmount is required")
    @Min(value = 0, message = "orderAmount must be greater than 0")
    private BigDecimal orderAmount;
}
