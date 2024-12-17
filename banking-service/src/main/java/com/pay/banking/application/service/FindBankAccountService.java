package com.pay.banking.application.service;

import com.pay.banking.adapter.out.psersistence.RegisteredBankAccountJpaEntity;
import com.pay.banking.adapter.out.psersistence.RegisteredBankAccountMapper;
import com.pay.banking.application.port.in.FindBankAccountCommand;
import com.pay.banking.application.port.in.FindBankAccountUseCase;
import com.pay.banking.application.port.out.FindBankAccountPort;
import com.pay.banking.domain.RegisteredBankAccount;
import com.pay.common.UseCase;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class FindBankAccountService implements FindBankAccountUseCase {
    private final FindBankAccountPort findBankAccountPort;


    private final RegisteredBankAccountMapper registeredBankAccountMapper;


    @Override
    public RegisteredBankAccount findBankAccount(FindBankAccountCommand command) {
        RegisteredBankAccountJpaEntity jpaEntity =findBankAccountPort.findRegisteredBankAccount(new RegisteredBankAccount.MembershipId(command.getMembershipId()));
        return  registeredBankAccountMapper.mapToDomainEntity(jpaEntity);
    }
}
