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
public class DecreaseMoneyRequestCommand extends SelfValidating<DecreaseMoneyRequestCommand> {
    @NonNull
    @NotBlank
    private  final String targetMembershipId;

    private final int amount;

    public DecreaseMoneyRequestCommand(@NonNull String targetMembershipId, int amount) {
        this.targetMembershipId = targetMembershipId;
        this.amount = amount;
        this.validateSelf();
    }
}
