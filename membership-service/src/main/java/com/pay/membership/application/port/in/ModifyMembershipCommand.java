package com.pay.membership.application.port.in;

import com.pay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)

public class ModifyMembershipCommand extends SelfValidating<ModifyMembershipCommand> {
    @NonNull
    @NotBlank
    private final String membershipId;
    @NonNull
    @NotBlank
    private final String name;

    @NonNull
    @NotBlank
    private final String email;

    @NonNull
    @NotBlank
    private final String address;

    @AssertTrue
    private final Boolean isValid;

    private final String aggregateIdentifier;

    public ModifyMembershipCommand(@NonNull String membershipId, @NonNull String name, @NonNull String email, @NonNull String address, Boolean isValid, String aggregateIdentifier) {
        this.membershipId = membershipId;
        this.name = name;
        this.email = email;
        this.address = address;
        this.isValid = isValid;
        this.aggregateIdentifier = aggregateIdentifier;
        this.validateSelf();
    }
}
