package com.pay.settlement.adapter.out.service.banking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisteredBankAccount {
    private String registeredBankAccountId;

    private String membershipId;

    private String bankName;

    private String bankAccountNumber;
    private boolean linkedStatusIsValid;
    private String aggregateIdentifier;
}