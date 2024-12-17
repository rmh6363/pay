package com.pay.banking.application.port.out;

import com.pay.banking.adapter.out.external.bank.BankAccount;
import com.pay.banking.adapter.out.external.bank.GetBankAccountRequest;

public interface RequestBankAccountInfoPort {
    BankAccount getBankAccountInfo(GetBankAccountRequest request);
}
