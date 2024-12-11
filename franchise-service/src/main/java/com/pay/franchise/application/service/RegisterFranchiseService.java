package com.pay.franchise.application.service;


import com.pay.common.UseCase;
import com.pay.franchise.adapter.out.psersistence.FranchiseMapper;
import com.pay.franchise.application.port.in.RegisteredFranchiseCase;
import com.pay.franchise.application.port.in.RegisteredFranchiseCommand;
import com.pay.franchise.application.port.out.RegisterFranchisePort;
import com.pay.franchise.domain.Franchise;
import lombok.RequiredArgsConstructor;


import javax.transaction.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RegisterFranchiseService implements RegisteredFranchiseCase {
    private final RegisterFranchisePort registerFranchisePort;

    private final FranchiseMapper franchiseMapper;

    @Override
    public Franchise registeredFranchiseCase(RegisteredFranchiseCommand command) {

        return franchiseMapper.mapToDomainEntity(registerFranchisePort.createFranchisee(
                new Franchise.Name(command.getName()),
                new Franchise.Contact(command.getContact()),
                new Franchise.BankName(command.getBankName()),
                new Franchise.BankAccountNumber(command.getBankAccountNumber()),
                new Franchise.IsValid(command.isValid())));
    }
}
