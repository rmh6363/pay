package com.pay.banking.adapter.axon.aggregate;



import com.pay.banking.adapter.axon.command.CreateFirmbankingRequestCommand;
import com.pay.banking.adapter.axon.command.UpdateFirmbankingRequestCommand;
import com.pay.banking.adapter.axon.event.FirmbankingRequestCreatedEvent;
import com.pay.banking.adapter.axon.event.FirmbankingRequestUpdatedEvent;
import com.pay.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import com.pay.banking.adapter.out.external.bank.FirmbankingResult;
import com.pay.banking.application.port.out.RequestExternalFirmbankingPort;
import com.pay.banking.application.port.out.RequestFirmbankingPort;
import com.pay.banking.domain.FirmBankingRequest;
import com.pay.common.event.RequestFirmbankingCommand;
import com.pay.common.event.RequestFirmbankingFinishedEvent;
import com.pay.common.event.RollbackFirmbankingFinishedEvent;
import com.pay.common.event.RollbackFirmbankingRequestCommand;
import lombok.Data;
import lombok.Getter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;



@Aggregate()
@Data
public class FirmbankingRequestAggregate {
    @AggregateIdentifier
    private String id;

    private String fromBankName;
    private String fromBankAccountNumber;
    private String toBankName;
    private String toBankAccountNumber;
    private int moneyAmount;
    private int firmbankingStatus; // 0: 요청 , 1: 완료, 2: 실패


    @CommandHandler
    public FirmbankingRequestAggregate(CreateFirmbankingRequestCommand command){
        System.out.println("CreateFirmbankingRequestCommand Handler");
        apply(new FirmbankingRequestCreatedEvent(command.getFromBankName(), command.getFromBankAccountNumber(), command.getToBankName(), command.getToBankAccountNumber(), command.getMoneyAmount()));
    }
    @EventSourcingHandler
    public void on(FirmbankingRequestCreatedEvent event){
        System.out.println("RequestFirmbankingCreatedEvent Sourcing Handler");
        id = UUID.randomUUID().toString();
        fromBankName = event.getFromBankName();
        fromBankAccountNumber = event.getFromBankAccountNumber();
        toBankName = event.getToBankName();
        toBankAccountNumber = event.getToBankAccountNumber();
    }

    @CommandHandler
    public String handle(UpdateFirmbankingRequestCommand command){
        System.out.println("UpdateFirmbankingRequestCommand Handler");
        id= command.getAggregateIdentifier();
        apply(new FirmbankingRequestUpdatedEvent(command.getFirmbankingStatus()));
        return id;
    }
    @EventSourcingHandler
    public void on(FirmbankingRequestUpdatedEvent event){
        System.out.println("FirmbankingRequestUpdatedEvent Sourcing Handler");
        firmbankingStatus = event.getFirmbankingStatus();
    }
    @CommandHandler
    public FirmbankingRequestAggregate(RequestFirmbankingCommand command, RequestFirmbankingPort firmbankingPort, RequestExternalFirmbankingPort externalFirmbankingPort){
        System.out.println("FirmbankingRequestAggregate Handler");
        id = command.getAggregateIdentifier();

        // from -> to
        // 펌뱅킹 수행!
        FirmbankingResult firmbankingResult;
        if (command.getMoneyAmount() > 0){
            firmbankingPort.createFirmbankingRequest(
                    new FirmBankingRequest.FromBankName(command.getFromBankName()),
                    new FirmBankingRequest.FromBankAccountNumber(command.getFromBankAccountNumber()),
                    new FirmBankingRequest.ToBankName("bank"),
                    new FirmBankingRequest.ToBankAccountNumber("123-333-9999"),
                    new FirmBankingRequest.MoneyAmount(command.getMoneyAmount()),
                    new FirmBankingRequest.FirmbankingStatus(0),
                    new FirmBankingRequest.AggregateIdentifier(id));

            // firmbanking!
            firmbankingResult = externalFirmbankingPort.requestExternalFirmbanking(
                    new ExternalFirmbankingRequest(
                            command.getFromBankName(),
                            command.getFromBankAccountNumber(),
                            command.getToBankName(),
                            command.getToBankAccountNumber(),
                            command.getMoneyAmount()
                    ));
        }else {
            firmbankingPort.createFirmbankingRequest(
                    new FirmBankingRequest.FromBankName(command.getToBankName()),
                    new FirmBankingRequest.FromBankAccountNumber(command.getToBankAccountNumber()),
                    new FirmBankingRequest.ToBankName("bank"),
                    new FirmBankingRequest.ToBankAccountNumber("123-333-9999"),
                    new FirmBankingRequest.MoneyAmount(command.getMoneyAmount()),
                    new FirmBankingRequest.FirmbankingStatus(0),
                    new FirmBankingRequest.AggregateIdentifier(id));

            // firmbanking!
            firmbankingResult = externalFirmbankingPort.requestExternalFirmbanking(
                    new ExternalFirmbankingRequest(
                            command.getFromBankName(),
                            command.getFromBankAccountNumber(),
                            command.getToBankName(),
                            command.getToBankAccountNumber(),
                            command.getMoneyAmount()
                    ));
        }


        int resultCode = firmbankingResult.getResultCode();

        // 0. 성공, 1. 실패
        apply(new RequestFirmbankingFinishedEvent(
                command.getRequestFirmbankingId(),
                command.getRechargeRequestId(),
                command.getMembershipId(),
                command.getToBankName(),
                command.getToBankAccountNumber(),
                command.getMoneyAmount(),
                resultCode,
                id
        ));
    }

    @CommandHandler
    public FirmbankingRequestAggregate(@NotNull RollbackFirmbankingRequestCommand command, RequestFirmbankingPort firmbankingPort, RequestExternalFirmbankingPort externalFirmbankingPort) {
        System.out.println("RollbackFirmbankingRequestCommand Handler");
        id = UUID.randomUUID().toString();

        // rollback 수행 (-> 법인 계좌 -> 고객 계좌 펌뱅킹)
        FirmbankingResult result;
        if (command.getMoneyAmount()  > 0){
            firmbankingPort.createFirmbankingRequest(
                    new FirmBankingRequest.FromBankName("pay-bank"),
                    new FirmBankingRequest.FromBankAccountNumber("123-333-9999"),
                    new FirmBankingRequest.ToBankName(command.getBankName()),
                    new FirmBankingRequest.ToBankAccountNumber(command.getBankAccountNumber()),
                    new FirmBankingRequest.MoneyAmount(command.getMoneyAmount()),
                    new FirmBankingRequest.FirmbankingStatus(0),
                    new FirmBankingRequest.AggregateIdentifier(id));

            // firmbanking!
            result = externalFirmbankingPort.requestExternalFirmbanking(
                    new ExternalFirmbankingRequest(
                            "pay",
                            "123-333-9999",
                            command.getBankName(),
                            command.getBankAccountNumber(),
                            command.getMoneyAmount()
                    ));
        }else {
            firmbankingPort.createFirmbankingRequest(
                    new FirmBankingRequest.FromBankName(command.getBankName()),
                    new FirmBankingRequest.FromBankAccountNumber(command.getBankAccountNumber()),
                    new FirmBankingRequest.ToBankName("pay-bank"),
                    new FirmBankingRequest.ToBankAccountNumber("123-333-9999"),
                    new FirmBankingRequest.MoneyAmount(command.getMoneyAmount()),
                    new FirmBankingRequest.FirmbankingStatus(0),
                    new FirmBankingRequest.AggregateIdentifier(id));

            // firmbanking!
            result = externalFirmbankingPort.requestExternalFirmbanking(
                    new ExternalFirmbankingRequest(
                            command.getBankName(),
                            command.getBankAccountNumber(),
                            "pay",
                            "123-333-9999",
                            command.getMoneyAmount()
                    ));
        }



        if (result.getResultCode() == 0){
            apply(new RollbackFirmbankingFinishedEvent(
                    command.getRollbackFirmbankingId(),
                    command.getMembershipId(),
                    id)
            );

        }
    }

    public FirmbankingRequestAggregate() {
    }
}