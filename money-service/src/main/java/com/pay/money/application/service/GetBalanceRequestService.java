package com.pay.money.application.service;


import com.pay.common.UseCase;
import com.pay.money.adapter.out.psersistence.GetBalanceRequestMapper;
import com.pay.money.application.port.in.GetBalanceRequestCommand;
import com.pay.money.application.port.in.GetBalanceRequestUseCase;
import com.pay.money.application.port.out.GetBalancePort;
import com.pay.money.domain.MemberMoney;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class GetBalanceRequestService implements GetBalanceRequestUseCase {
    private final GetBalancePort getBalancePort;

    private final GetBalanceRequestMapper getBalanceRequestMapper;


    @Override
    public MemberMoney getBalanceRequest(GetBalanceRequestCommand command) {
        return getBalanceRequestMapper.mapToDomainEntity(getBalancePort.getBalance(command.getTargetMembershipId()));
    }
}
