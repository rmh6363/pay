package com.pay.money.adapter.axon.command;

import com.pay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class MemberMoneyCreatedCommand extends SelfValidating<MemberMoneyCreatedCommand> {
    @NonNull
    @NotBlank
    private String membershipId;



    public MemberMoneyCreatedCommand(@NonNull String membershipId) {
        this.membershipId = membershipId;

        this.validateSelf();
    }

    public MemberMoneyCreatedCommand() {
        // Required by Axon to construct an empty instance to initiate Event Sourcing.
    }
}
