package com.pay.membership.application.port.in;

import common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class RegisterMembershipCommand extends SelfValidating<RegisterMembershipCommand> {
    @NonNull
    @NotBlank
    private  final String name;

    @NonNull
    @NotBlank
    private final String email;

    @NonNull
    @NotBlank
    private final String address;

    @AssertTrue
    private final Boolean isValid;

    private final String aggregateIdentifier;

    public RegisterMembershipCommand(@NonNull String name, @NonNull String email, @NonNull String address, Boolean isValid, String aggregateIdentifier) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.isValid = isValid;
        this.aggregateIdentifier = aggregateIdentifier;
        this.validateSelf();
    }
}
