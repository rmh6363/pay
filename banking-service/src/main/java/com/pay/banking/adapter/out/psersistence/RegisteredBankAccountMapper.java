package com.pay.banking.adapter.out.psersistence;


import com.pay.banking.domain.RegisteredBankAccount;
import org.springframework.stereotype.Component;

@Component
public class RegisteredBankAccountMapper {
    public RegisteredBankAccount mapToDomainEntity(RegisteredBankAccountJpaEntity registeredBankAccountJpaEntity){
        return RegisteredBankAccount.generateRegisterBankAccount(
                new RegisteredBankAccount.RegisterBankAccountId(registeredBankAccountJpaEntity.getRegisterBankAccountId()+""),
                new RegisteredBankAccount.MembershipId(registeredBankAccountJpaEntity.getMembershipId()+""),
                new RegisteredBankAccount.BankName(registeredBankAccountJpaEntity.getBankName()),
                new RegisteredBankAccount.BankAccountNumber(registeredBankAccountJpaEntity.getBankAccountNumber()),
                new RegisteredBankAccount.LinkedStatusIsValid(registeredBankAccountJpaEntity.isLinkedStatusIsValid())
        );
    }
}

