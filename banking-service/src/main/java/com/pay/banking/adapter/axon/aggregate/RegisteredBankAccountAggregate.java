package com.pay.banking.adapter.axon.aggregate;

import com.pay.banking.adapter.axon.command.CreateRegisteredBankAccountCommand;
import com.pay.banking.adapter.axon.event.CreateRegisteredBankAccountEvent;
import com.pay.banking.adapter.out.external.bank.BankAccount;
import com.pay.banking.adapter.out.external.bank.GetBankAccountRequest;
import com.pay.banking.application.port.out.GetMembershipPort;
import com.pay.banking.application.port.out.MembershipStatus;
import com.pay.banking.application.port.out.RequestBankAccountInfoPort;
import com.pay.common.event.CheckRegisteredBankAccountCommand;
import com.pay.common.event.CheckedRegisteredBankAccountEvent;
import lombok.Data;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import javax.validation.constraints.NotNull;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@Data
public class RegisteredBankAccountAggregate {
    @AggregateIdentifier
    private String id;

    private String membershipId;

    private String bankName;

    private String bankAccountNumber;

    @CommandHandler
    public RegisteredBankAccountAggregate(@NotNull CreateRegisteredBankAccountCommand command,GetMembershipPort getMembershipPort){
        System.out.println("CreateRegisteredBankAccountCommand ");
        id = command.getAggregateIdentifier();
        MembershipStatus membershipStatus = getMembershipPort.getMembership(command.getMembershipId());
        apply(new CreateRegisteredBankAccountEvent(
                command.getMembershipId(),
                command.getBankName(),
                command.getBankAccountNumber(),
                membershipStatus.getAggregateIdentifier()
        ));

    }

    @CommandHandler
    public void handle(@NotNull CheckRegisteredBankAccountCommand command, RequestBankAccountInfoPort port){
        System.out.println("CreateRegisteredBankAccountCommand Handler");
        // command 를 통해, RegisteredBankAccount어그리거트 정상인지 확인
        id = command.getAggregateIdentifier();

        //check RegisteredBankAccount
        BankAccount bankAccount =port.getBankAccountInfo(new GetBankAccountRequest(command.getBankName(),command.getBankAccountNumber()));
        boolean isValidAccount = bankAccount.isValid();

        String firmbankingUUID = UUID.randomUUID().toString();

        //CheckedRegisteredBankAccountEvent
        apply(new CheckedRegisteredBankAccountEvent(
                        command.getRechargeRequestId()
                        , command.getCheckRegisteredBankAccountId()
                        , command.getMembershipId()
                        , isValidAccount
                        , command.getAmount()
                        , firmbankingUUID
                        , bankAccount.getBankName()
                        , bankAccount.getBankAccounNumber()
                )
        );

    }

    @EventSourcingHandler
    public void on(CreateRegisteredBankAccountEvent event ){
        System.out.println("CreateRegisteredBankAccountEvent");
        id = event.getAggregateIdentifier();
        membershipId = event.getMembershipId();
        bankAccountNumber = event.getBankAccountNumber();
        bankName = event.getBankName();
    }
    public RegisteredBankAccountAggregate() {
    }
}
