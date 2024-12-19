package com.pay.membership.adapter.axon.aggregate;


import com.pay.common.event.CheckRegisteredBankAccountCommand;
import com.pay.common.event.CheckedRegisteredBankAccountEvent;
import com.pay.membership.adapter.axon.command.MembershipCreatedCommand;
import com.pay.membership.adapter.axon.event.MembershipCreatedEvent;
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
public class RegisteredMembershipAggregate {
    //test111111
    @AggregateIdentifier
    private String id;

    private  String name;


    private String email;


    private String address;


    private Boolean isValid;

    @CommandHandler
    public RegisteredMembershipAggregate(@NotNull MembershipCreatedCommand command){
        System.out.println("MembershipCreatedCommand ");
        apply(new MembershipCreatedEvent(command.getName(),command.getEmail(),command.getAddress(),command.getIsValid()));

    }

    @EventSourcingHandler
    public void on(MembershipCreatedEvent event){
        System.out.println("MembershipCreatedEvent");
        id = UUID.randomUUID().toString();
        name = event.getName();
        email = event.getEmail();
        address = event.getAddress();
        isValid = event.getIsValid();
    }
    public RegisteredMembershipAggregate() {
    }
}
