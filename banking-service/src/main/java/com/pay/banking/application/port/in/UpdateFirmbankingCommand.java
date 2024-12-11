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
public class UpdateFirmbankingCommand extends SelfValidating<UpdateFirmbankingCommand> {
    @NonNull
    @NotBlank
    private  final String firmbankingRequestAggregateIdentifier;

    @NonNull
    private  final int firmbankingStatus;


}
