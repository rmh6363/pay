package com.pay.banking.application.port.in;

import com.pay.banking.domain.RegisteredBankAccount;


public interface FindBankAccountUseCase {
    RegisteredBankAccount findBankAccount(FindBankAccountCommand command);

}
