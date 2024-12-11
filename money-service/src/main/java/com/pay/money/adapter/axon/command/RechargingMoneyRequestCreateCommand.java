package com.pay.money.adapter.axon.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RechargingMoneyRequestCreateCommand {
    @TargetAggregateIdentifier
    @NotNull
    private String aggregateIdentifier;
    private String rechargingRequestId;

    private String membershipId;
    private int amount;
}
