package com.pay.franchise.application.service;

import com.pay.common.UseCase;
import com.pay.franchise.adapter.out.psersistence.FranchiseMapper;
import com.pay.franchise.application.port.in.FindFranchiseCommand;
import com.pay.franchise.application.port.in.FindFranchiseUseCase;
import com.pay.franchise.application.port.out.FindFranchisePort;
import com.pay.franchise.domain.Franchise;
import lombok.RequiredArgsConstructor;
import javax.transaction.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class FindFranchiseService implements FindFranchiseUseCase {
    private final FindFranchisePort findFranchisePort;

    private final FranchiseMapper franchiseMapper;


    @Override
    public Franchise findFranchiseUseCase(FindFranchiseCommand command) {
        return franchiseMapper.mapToDomainEntity(findFranchisePort.FindFranchisee(
                new Franchise.FranchiseeId(command.getFranchiseeId())
        ));
    }
}
