package com.pay.banking.application.port.in;

import com.pay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class FindFirmbankingCommand extends SelfValidating<FindFirmbankingCommand> {
    private final String fromBankAccountNumber;
    private final String fromBankName;
}
