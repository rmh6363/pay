package com.pay.membership.adapter.axon.command;

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
public class MembershipCreatedCommand extends SelfValidating<MembershipCreatedCommand> {
    @NonNull
    @NotBlank
    private  String name;

    @NonNull
    @NotBlank
    private String email;

    @NonNull
    @NotBlank
    private String address;

    @AssertTrue
    private Boolean isValid;

    private String aggregateIdentifier;

    public MembershipCreatedCommand(@NonNull String name, @NonNull String email, @NonNull String address, Boolean isValid, String aggregateIdentifier) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.isValid = isValid;
        this.aggregateIdentifier = aggregateIdentifier;
        this.validateSelf();
    }
}
