package com.pay.payment.application.port.out;

import com.pay.payment.domain.Payment;

import java.util.List;

public interface CreatePaymentPort {
    Payment createPayment(String requestMembershipId, String requestPrice, String franchiseId, String franchiseFeeRate);

    List<Payment> getNormalStatusPayments();
    Payment changePaymentRequestStatus(String paymentId, int status);
}
