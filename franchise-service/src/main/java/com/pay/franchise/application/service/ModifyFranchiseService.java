package com.pay.franchise.application.service;

import com.pay.common.UseCase;
import com.pay.franchise.adapter.out.psersistence.FranchiseMapper;
import com.pay.franchise.application.port.in.FindFranchiseCommand;
import com.pay.franchise.application.port.in.FindFranchiseUseCase;
import com.pay.franchise.application.port.in.ModifyFranchiseCommand;
import com.pay.franchise.application.port.in.ModifyFranchiseUseCase;
import com.pay.franchise.application.port.out.FindFranchisePort;
import com.pay.franchise.application.port.out.ModifyFranchisePort;
import com.pay.franchise.domain.Franchise;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class ModifyFranchiseService implements ModifyFranchiseUseCase {
    private final ModifyFranchisePort modifyFranchisePort;

    private final FranchiseMapper franchiseMapper;




    @Override
    public Franchise modifyFranchiseUseCase(ModifyFranchiseCommand command) {
        return franchiseMapper.mapToDomainEntity(modifyFranchisePort.ModifyFranchisee(
                new Franchise.FranchiseeId(command.getFranchiseeId()+""),
                new Franchise.Name(command.getName()),
                new Franchise.Contact(command.getContact()),
                new Franchise.BankName(command.getBankName()),
                new Franchise.BankAccountNumber(command.getBankAccountNumber()),
                new Franchise.IsValid(command.isValid())
        ));
    }
}
