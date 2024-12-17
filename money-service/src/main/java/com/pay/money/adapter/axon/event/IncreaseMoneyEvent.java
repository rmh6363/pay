package com.pay.money.adapter.axon.event;

import com.pay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.NotBlank;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class IncreaseMoneyEvent extends SelfValidating<IncreaseMoneyEvent> {
    @NonNull
    @TargetAggregateIdentifier
    private final String aggregateIdentifier;
    @NonNull
    @NotBlank
    private final String targetMembershipId;


    @NonNull
    private final int amount;

    public IncreaseMoneyEvent(@NonNull String aggregateIdentifier, @NonNull String targetMembershipId, @NonNull int amount) {
        this.aggregateIdentifier = aggregateIdentifier;
        this.targetMembershipId = targetMembershipId;
        this.amount = amount;
    }


}
