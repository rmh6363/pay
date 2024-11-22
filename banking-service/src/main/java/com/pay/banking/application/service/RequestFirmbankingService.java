package com.pay.banking.application.service;

import com.pay.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import com.pay.banking.adapter.out.external.bank.FirmbankingResult;
import com.pay.banking.adapter.out.psersistence.FirmbankingRequestEntity;
import com.pay.banking.adapter.out.psersistence.FirmbankingRequestMapper;
import com.pay.banking.application.port.in.RequestFirmbankingUseCase;
import com.pay.banking.application.port.in.RequestFirmbankingCommand;
import com.pay.banking.application.port.out.RequestExternalFirmbankingPort;
import com.pay.banking.application.port.out.RequestFirmbankingPort;
import com.pay.banking.domain.FirmBankingRequest;
import com.pay.common.UseCase;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RequestFirmbankingService implements RequestFirmbankingUseCase {
    private final RequestFirmbankingPort requestFirmbankingPort;

    private final RequestExternalFirmbankingPort requestExternalFirmbankingPort;
    private final FirmbankingRequestMapper mapper;

    @Override
    public FirmBankingRequest requestFirmbanking(RequestFirmbankingCommand command) {

        //Business Logic
        // a -> b 계좌

        // 1. 요청에 대해 정보를 먼저 write.
        FirmbankingRequestEntity requestedEntity = requestFirmbankingPort.createFirmbankingRequest(
                new FirmBankingRequest.FromBankName(command.getFromBankName()),
                new FirmBankingRequest.FromBankAccountNumber(command.getFromBankAccountNumber()),
                new FirmBankingRequest.ToBankName(command.getToBankName()),
                new FirmBankingRequest.ToBankAccountNumber(command.getToBankAccountNumber()),
                new FirmBankingRequest.MoneyAmount(command.getMoneyAmount()),
                new FirmBankingRequest.FirmbankingStatus(0)
        );

        // 2. 외부 은행에 펌뱅킹 요청
        FirmbankingResult result = requestExternalFirmbankingPort.requestExternalFirmbanking(new ExternalFirmbankingRequest(
                command.getFromBankName(),
                command.getFromBankAccountNumber(),
                command.getToBankName(),
                command.getToBankAccountNumber()
        ));

        // Transactional UUID
        UUID randomUUID = UUID.randomUUID();
        requestedEntity.setUuid(randomUUID.toString());
        // 3. 결과에 따라서 1번에서 작성했던 FirmbankingRequest 정보를 Update
        if (result.getResultCode() == 0) {
            //성공
            requestedEntity.setFirmbankingStatus(1);
        } else {
            //실패
            requestedEntity.setFirmbankingStatus(2);
        }
        //4. 결과를 리턴하기 전에 바뀐 상태 값을 기준으로 다시 save
        return mapper.mapToDomainEntity(requestFirmbankingPort.modifyFirmbankingRequest(requestedEntity), randomUUID);
    }
}
