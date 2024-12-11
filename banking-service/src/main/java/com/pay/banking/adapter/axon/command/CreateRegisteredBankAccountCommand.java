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
public class CreateRegisteredBankAccountCommand extends SelfValidating<CreateRegisteredBankAccountCommand> {


    private String membershipId;
    private String bankAccountNumber;
    private String bankName;



}
