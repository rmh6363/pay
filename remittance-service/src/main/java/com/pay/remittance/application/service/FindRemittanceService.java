package com.pay.remittance.application.service;


import com.pay.common.UseCase;
import com.pay.remittance.adapter.out.persistence.RemittanceRequestJpaEntity;
import com.pay.remittance.adapter.out.persistence.RemittanceRequestMapper;
import com.pay.remittance.application.port.in.FindRemittanceCommand;
import com.pay.remittance.application.port.in.FindRemittanceUseCase;
import com.pay.remittance.application.port.in.RequestRemittanceCommand;
import com.pay.remittance.application.port.in.RequestRemittanceUseCase;
import com.pay.remittance.application.port.out.FindRemittancePort;
import com.pay.remittance.application.port.out.RequestRemittancePort;
import com.pay.remittance.application.port.out.banking.BankingPort;
import com.pay.remittance.application.port.out.membership.MembershipPort;
import com.pay.remittance.application.port.out.membership.MembershipStatus;
import com.pay.remittance.application.port.out.money.MoneyInfo;
import com.pay.remittance.application.port.out.money.MoneyPort;
import com.pay.remittance.domain.RemittanceRequest;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;

@UseCase
@RequiredArgsConstructor
@Transactional
public class FindRemittanceService implements FindRemittanceUseCase {


    private final RemittanceRequestMapper mapper;
    private final FindRemittancePort findRemittancePort;



    @Override
    public List<RemittanceRequest> findRemittanceHistory(FindRemittanceCommand command) {
        return mapper.mapToDomainEntities(findRemittancePort.findRemittanceHistory(command));
    }
}

