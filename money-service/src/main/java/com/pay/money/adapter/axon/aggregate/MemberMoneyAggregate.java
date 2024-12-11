package com.pay.money.adapter.axon.aggregate;


import com.pay.money.adapter.axon.command.IncreaseMemberMoneyCommand;
import com.pay.money.adapter.axon.command.MemberMoneyCreatedCommand;

import com.pay.money.adapter.axon.command.RechargingMoneyRequestCreateCommand;
import com.pay.money.adapter.axon.event.IncreaseMoneyEvent;
import com.pay.money.adapter.axon.event.MemberMoneyCreatedEvent;
import com.pay.money.adapter.axon.event.RechargingRequestCreatedEvent;
import com.pay.money.adapter.out.service.banking.BankingInfo;
import com.pay.money.application.port.out.banking.BankingPort;
import lombok.Data;
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
public class MemberMoneyAggregate {
    @AggregateIdentifier
    private String id;

    private Long membershipId;

    private int balance;

    @CommandHandler
    public MemberMoneyAggregate(MemberMoneyCreatedCommand command) {
        System.out.println("MemberMoneyCreatedCommand");

        apply(new MemberMoneyCreatedEvent(command.getMembershipId()));

    }
    @EventSourcingHandler
    public void on(MemberMoneyCreatedEvent event) {
        System.out.println("MemberMoneyCreatedEvent Sourcing Handler");
        id = UUID.randomUUID().toString();
        membershipId = Long.parseLong(event.getMembershipId());
        balance = 0;
    }
    @CommandHandler
    public String handle(@NotNull IncreaseMemberMoneyCommand command) {
        System.out.println("IncreaseMemberMoneyCommand Handler");
        id = command.getAggregateIdentifier();

        // store event
        apply(new IncreaseMoneyEvent(id, command.getTargetMembershipId(), command.getAmount()));
        return id;
    }
    @EventSourcingHandler
    public void on(IncreaseMoneyEvent event) {
        System.out.println("IncreaseMoneyEvent Sourcing Handler");
        id = UUID.randomUUID().toString();
        membershipId = Long.parseLong(event.getTargetMembershipId());
        balance = event.getAmount();
    }

    @CommandHandler
    public void handler(RechargingMoneyRequestCreateCommand command, BankingPort port){
        System.out.println("RechargingMoneyRequestCreateCommand");
        id = command.getAggregateIdentifier();
        // new RechargingRequestCreatedEvent
        // banking 정보가 필요해요. -> bank svc (get RegisteredBankAccount) 를 위한. Port.
        BankingInfo bankingInfo =port.getMembershipBankingInfo(command.getMembershipId());
        System.out.println("bankingInfo = " + bankingInfo);
        // Saga Start
        apply(new RechargingRequestCreatedEvent(
                command.getRechargingRequestId(),
                command.getMembershipId(),
                command.getAmount(),
                bankingInfo.getAggregateIdentifier(),
                bankingInfo.getBankName(),
                bankingInfo.getBankAccountNumber()
        ));
    }
    public MemberMoneyAggregate() {

    }

}