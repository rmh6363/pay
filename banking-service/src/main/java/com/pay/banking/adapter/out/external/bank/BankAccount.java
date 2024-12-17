package com.pay.banking.adapter.out.external.bank;

import lombok.Data;
import lombok.Getter;

@Data
public class BankAccount {
    private String bankName;
    private String bankAccounNumber;
    @Getter
    private boolean isValid;

    public BankAccount(String bankName, String bankAccounNumber, boolean isValid) {
        this.bankName = bankName;
        this.bankAccounNumber = bankAccounNumber;
        this.isValid = isValid;
    }
}
