package com.pay.banking.application.service;

import com.pay.banking.adapter.axon.command.CreateFirmbankingRequestCommand;
import com.pay.banking.adapter.axon.command.UpdateFirmbankingRequestCommand;
import com.pay.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import com.pay.banking.adapter.out.external.bank.FirmbankingResult;
import com.pay.banking.adapter.out.psersistence.FirmbankingRequestEntity;
import com.pay.banking.adapter.out.psersistence.FirmbankingRequestMapper;
import com.pay.banking.application.port.in.RequestFirmbankingUseCase;
import com.pay.banking.application.port.in.RequestFirmbankingCommand;
import com.pay.banking.application.port.in.UpdateFirmbankingCommand;
import com.pay.banking.application.port.in.UpdateFirmbankingUseCase;
import com.pay.banking.application.port.out.RequestExternalFirmbankingPort;
import com.pay.banking.application.port.out.RequestFirmbankingPort;
import com.pay.banking.domain.FirmBankingRequest;
import com.pay.common.UseCase;
import io.axoniq.axonserver.grpc.command.Command;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;

import javax.transaction.Transactional;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RequestFirmbankingService implements RequestFirmbankingUseCase, UpdateFirmbankingUseCase {
    private final RequestFirmbankingPort requestFirmbankingPort;

    private final RequestExternalFirmbankingPort requestExternalFirmbankingPort;
    private final FirmbankingRequestMapper mapper;
    private final CommandGateway commandGateway;

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
                new FirmBankingRequest.FirmbankingStatus(0),
                new FirmBankingRequest.AggregateIdentifier("")
        );

        // 2. 외부 은행에 펌뱅킹 요청
        FirmbankingResult result = requestExternalFirmbankingPort.requestExternalFirmbanking(new ExternalFirmbankingRequest(
                command.getFromBankName(),
                command.getFromBankAccountNumber(),
                command.getToBankName(),
                command.getToBankAccountNumber(),
                command.getMoneyAmount()
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

    @Override
    public void requestFirmbankingByEvent(RequestFirmbankingCommand command) {
        // comand -> Event Sourcing
        CreateFirmbankingRequestCommand createFirmbankingRequestCommand = CreateFirmbankingRequestCommand.builder()
                .toBankAccountNumber(command.getToBankAccountNumber())
                .toBankName(command.getToBankName())
                .fromBankAccountNumber(command.getFromBankAccountNumber())
                .fromBankName(command.getFromBankName())
                .moneyAmount(command.getMoneyAmount())
                .build();
        commandGateway.send(createFirmbankingRequestCommand)
                .whenComplete((result, throwable) -> {
                    if (throwable != null) {
                        System.out.println("throwable = " + throwable);
                        throwable.printStackTrace();
                    } else {
                        //Request Firmbanking
                        FirmbankingRequestEntity requestedEntity = requestFirmbankingPort.createFirmbankingRequest(
                                new FirmBankingRequest.FromBankName(command.getFromBankName()),
                                new FirmBankingRequest.FromBankAccountNumber(command.getFromBankAccountNumber()),
                                new FirmBankingRequest.ToBankName(command.getToBankName()),
                                new FirmBankingRequest.ToBankAccountNumber(command.getToBankAccountNumber()),
                                new FirmBankingRequest.MoneyAmount(command.getMoneyAmount()),
                                new FirmBankingRequest.FirmbankingStatus(0),
                                new FirmBankingRequest.AggregateIdentifier(result.toString())
                        );
                        // 2. 외부 은행에 펌뱅킹 요청
                        FirmbankingResult requset = requestExternalFirmbankingPort.requestExternalFirmbanking(new ExternalFirmbankingRequest(
                                command.getFromBankName(),
                                command.getFromBankAccountNumber(),
                                command.getToBankName(),
                                command.getToBankAccountNumber(),
                                command.getMoneyAmount()
                        ));

                        // Transactional UUID
                        UUID randomUUID = UUID.randomUUID();
                        requestedEntity.setUuid(randomUUID.toString());
                        // 3. 결과에 따라서 1번에서 작성했던 FirmbankingRequest 정보를 Update
                        if (requset.getResultCode() == 0) {
                            //성공
                            requestedEntity.setFirmbankingStatus(1);
                        } else {
                            //실패
                            requestedEntity.setFirmbankingStatus(2);
                        }
                    }
                });
    }

    @Override
    public void updateFirmbankingByEvent(UpdateFirmbankingCommand command) {
        UpdateFirmbankingRequestCommand updateFirmbankingRequestCommand =
                new UpdateFirmbankingRequestCommand(command.getFirmbankingRequestAggregateIdentifier(),command.getFirmbankingStatus());
        commandGateway.send(updateFirmbankingRequestCommand)
                .whenComplete((result, throwable) -> {
                    if (throwable != null) {
                        System.out.println("throwable = " + throwable);
                        throwable.printStackTrace();
                    } else {
                        System.out.println("result = " + result);
                        FirmbankingRequestEntity entity=requestFirmbankingPort.getFirmbankingRequest(new FirmBankingRequest.AggregateIdentifier(command.getFirmbankingRequestAggregateIdentifier()));
                        // status 의 견경으로 인한 외부 은행과의 커뮤니케이션
                        // if rollback -> 0 ,status 변경
                        // + 기본 펌뱅킹 정보에서 from <-> to 가 변경된 펌뱅킹을 요청하는 새로운 요청
                        entity.setFirmbankingStatus(command.getFirmbankingStatus());
                        requestFirmbankingPort.modifyFirmbankingRequest(entity);

                    }
                });
    }
}
