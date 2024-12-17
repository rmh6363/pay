package com.pay.money.adapter.out.service.banking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RegisteredBankAccountResponseBody {
    private String registeredBankAccountId;

    private String membershipId;

    private String bankName;

    private String bankAccountNumber;
    private boolean linkedStatusIsValid;
    private String aggregateIdentifier;
}
