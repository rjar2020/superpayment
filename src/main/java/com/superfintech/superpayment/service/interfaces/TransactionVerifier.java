package com.superfintech.superpayment.service.interfaces;

public interface TransactionVerifier {
    boolean verifyTransaction(String companyId, String code, String voucher);
}
