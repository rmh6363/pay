package com.pay.banking.application.port.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembershipStatus {
    private String membershipId;

    public String aggregateIdentifier;
    private boolean isValid;
}
