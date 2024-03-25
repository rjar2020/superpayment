package com.superfintech.superpayment.service;

import com.superfintech.superpayment.service.interfaces.TransactionVerifier;
import org.springframework.stereotype.Service;

@Service
public class VoucherTransactionVerifier implements TransactionVerifier {
    public boolean verifyTransaction(String companyId, String code, String voucher) {
        if(voucher == null || voucher.isEmpty() || code == null || code.isEmpty() || companyId == null || companyId.isEmpty()) {
            return false;
        }

        if (voucher.contains("error")) {
            return false;
        } else if (voucher.contains("accepted")) {
            return true;
        } else {
            return Math.random() > 0.8;
        }

    }
}
