package com.pay.banking.adapter.out.psersistence;

import com.pay.banking.application.port.out.FindBankAccountPort;
import com.pay.banking.application.port.out.RegisterBankAccountPort;
import com.pay.banking.domain.RegisteredBankAccount;
import com.pay.common.PersistenceAdapter;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class RegisteredBankAccountPersistenceAdapter implements RegisterBankAccountPort, FindBankAccountPort {
    private final SpringDataRegisteredBankAccountRepository bankAccountRepository;


    @Override
    public RegisteredBankAccountJpaEntity createRegisteredBankAccount(RegisteredBankAccount.MembershipId membershipId, RegisteredBankAccount.BankName bankName, RegisteredBankAccount.BankAccountNumber bankAccountNumber, RegisteredBankAccount.LinkedStatusIsValid linkedStatusIsValid,RegisteredBankAccount.AggregateIdentifier aggregateIdentifier) {

        return bankAccountRepository.save(new RegisteredBankAccountJpaEntity(
                membershipId.getMembershipId(),
                bankName.getBankName(),
                bankAccountNumber.getBankAccountNumber(),
                linkedStatusIsValid.isLinkedStatusIsValid(),
                aggregateIdentifier.getAggregateIdentifier()
        ));
    }

    @Override
    public long countRegisteredBankAccount(RegisteredBankAccount.BankName bankName, RegisteredBankAccount.BankAccountNumber bankAccountNumber) {
        return bankAccountRepository.countByBankNameAndBankAccountNumber(bankName.getBankName(), bankAccountNumber.getBankAccountNumber());
    }


    @Override
    public RegisteredBankAccountJpaEntity findRegisteredBankAccount(RegisteredBankAccount.MembershipId membershipId) {
        return bankAccountRepository.findByMembershipId(membershipId.getMembershipId());
    }
}
