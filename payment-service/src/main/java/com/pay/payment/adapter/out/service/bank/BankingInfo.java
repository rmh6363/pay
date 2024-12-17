package com.pay.payment.adapter.out.service.bank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BankingInfo {
    private String membershipId;
    private String bankName;
    private String bankAccountNumber;
    private boolean isValid;
}
