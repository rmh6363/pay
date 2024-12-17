package com.pay.banking.adapter.axon.command;

import com.pay.common.SelfValidating;
import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Data
@Builder
public class CreateFirmbankingRequestCommand extends SelfValidating<CreateFirmbankingRequestCommand> {
    @NotNull
    @TargetAggregateIdentifier
    private String aggregateIdentifier;

    private String fromBankName;
    private String fromBankAccountNumber;
    private String toBankName;
    private String toBankAccountNumber;
    private int moneyAmount;


}
