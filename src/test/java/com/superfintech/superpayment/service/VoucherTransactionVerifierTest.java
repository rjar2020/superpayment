package com.superfintech.superpayment.service;

import com.superfintech.superpayment.service.VoucherTransactionVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VoucherTransactionVerifierTest {

    private final VoucherTransactionVerifier verifier = new VoucherTransactionVerifier();

    @Test
    void verifyTransaction_ShouldReturnFalse_WhenInputsAreEmpty() {
        // Inputs are null
        Assertions.assertFalse(verifier.verifyTransaction(null, null, null));

        // Inputs are empty
        Assertions.assertFalse(verifier.verifyTransaction("", "", ""));
    }

    @Test
    void verifyTransaction_ShouldReturnFalse_WhenVoucherContainsError() {
        Assertions.assertFalse(verifier.verifyTransaction("company123", "code123", "errorVoucher"));
    }

    @Test
    void verifyTransaction_ShouldReturnTrue_WhenVoucherContainsAccepted() {
        Assertions.assertTrue(verifier.verifyTransaction("company123", "code123", "acceptedVoucher"));
    }
}
