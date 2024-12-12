package com.pay.settlement.port.out.payment;

import com.pay.settlement.adapter.out.service.payment.Payment;

import java.util.List;

public interface PaymentPort {
    List<Payment> getNormalStatusPayments(); // membershipId = franchiseId 간주.

    // 타겟 계좌, 금액
    void finishSettlement(Long paymentId);
}
