package com.pay.banking.application.port.in;

import com.pay.banking.domain.RegisteredBankAccount;



public interface RegisterBankAccountUseCase {
    RegisteredBankAccount registerBankAccount(RegisterBankAccountCommand command);
    void registerBankAccountByEvent(RegisterBankAccountCommand command);

}
