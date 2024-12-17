package com.pay.banking.application.service;

import com.pay.banking.adapter.out.psersistence.FirmbankingRequestEntity;
import com.pay.banking.adapter.out.psersistence.FirmbankingRequestMapper;
import com.pay.banking.adapter.out.psersistence.RegisteredBankAccountJpaEntity;
import com.pay.banking.adapter.out.psersistence.RegisteredBankAccountMapper;
import com.pay.banking.application.port.in.FindBankAccountCommand;
import com.pay.banking.application.port.in.FindBankAccountUseCase;
import com.pay.banking.application.port.in.FindFirmbankingCommand;
import com.pay.banking.application.port.in.FindFirmbankingUseCase;
import com.pay.banking.application.port.out.FindBankAccountPort;
import com.pay.banking.application.port.out.FindFirmbankingPort;
import com.pay.banking.domain.FirmBankingRequest;
import com.pay.banking.domain.RegisteredBankAccount;
import com.pay.common.UseCase;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
@Transactional
public class FindFirmbankingService implements FindFirmbankingUseCase {
    private final FindFirmbankingPort findFirmbankingPort;

    private final FirmbankingRequestMapper firmbankingRequestMapper;


    @Override
    public List<FirmBankingRequest> findFirmbanking(FindFirmbankingCommand command) {
        List<FirmbankingRequestEntity> jpaEntity = findFirmbankingPort.findfirmbanking(
                new FirmBankingRequest.FromBankAccountNumber(command.getFromBankAccountNumber()),
                new FirmBankingRequest.FromBankName(command.getFromBankName()));
        UUID randomUUID = UUID.randomUUID();
        return firmbankingRequestMapper.mapToDomainEntities(jpaEntity,randomUUID);
    }
}
