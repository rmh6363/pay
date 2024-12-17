package com.pay.money.application.port.in;

import com.pay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class GetBalanceRequestCommand extends SelfValidating<GetBalanceRequestCommand> {
    @NonNull
    @NotBlank
    private  final String targetMembershipId;



    public GetBalanceRequestCommand(@NonNull String targetMembershipId) {
        this.targetMembershipId = targetMembershipId;
        this.validateSelf();
    }
}
