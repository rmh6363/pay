package com.pay.banking.adapter.out.external.bank;

import lombok.Data;

@Data
public class GetBankAccountRequest {
    private String bankName;
    private String bankAccountNumber;
    private boolean isValid;

    public GetBankAccountRequest(String bankName, String bankAccountNumber, boolean isValid) {
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
        this.isValid = isValid;
    }
}
