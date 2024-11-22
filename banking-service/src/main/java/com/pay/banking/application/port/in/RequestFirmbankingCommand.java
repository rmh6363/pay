package com.pay.banking.application.port.in;

import com.pay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class RequestFirmbankingCommand extends SelfValidating<RequestFirmbankingCommand> {
    @NonNull
    @NotBlank
    private  final String fromBankName;

    @NonNull
    @NotBlank
    private final String fromBankAccountNumber;

    @NonNull
    @NotBlank
    private final String toBankName;

    @NonNull
    @NotBlank
    private final String toBankAccountNumber;

    private final int moneyAmount;

    public RequestFirmbankingCommand(@NonNull String fromBankName, @NonNull String fromBankAccountNumber, @NonNull String toBankName, @NonNull String toBankAccountNumber, int moneyAmount) {
        this.fromBankName = fromBankName;
        this.fromBankAccountNumber = fromBankAccountNumber;
        this.toBankName = toBankName;
        this.toBankAccountNumber = toBankAccountNumber;
        this.moneyAmount = moneyAmount;
    }
}
