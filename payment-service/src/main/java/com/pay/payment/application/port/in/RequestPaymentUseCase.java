package com.pay.payment.application.port.in;

import com.pay.payment.domain.Payment;

import java.util.List;

public interface RequestPaymentUseCase {
    Payment requestPayment(RequestPaymentCommand command);

    // 원래대로라면,, command . start date, end date
    List<Payment> getNormalStatusPayments();

    Payment finishPayment(FinishSettlementCommand command);
}
