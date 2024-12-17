package com.pay.banking.adapter.axon.event;

import com.pay.common.SelfValidating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Data
public class CreateRegisteredBankAccountEvent extends SelfValidating<CreateRegisteredBankAccountEvent> {

    private String membershipId;

    private String bankName;
    private String bankAccountNumber;

    private String aggregateIdentifier;


}
